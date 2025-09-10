package com.circle.schedulemanager.controller;

import com.circle.schedulemanager.entity.Event;
import com.circle.schedulemanager.entity.Member;
import com.circle.schedulemanager.entity.Participation;
import com.circle.schedulemanager.entity.ParticipationStatus;
import com.circle.schedulemanager.service.EventService;
import com.circle.schedulemanager.service.MemberService;
import com.circle.schedulemanager.service.ParticipationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/events")
public class EventController {
    
    @Autowired
    private EventService eventService;
    
    @Autowired
    private MemberService memberService;
    
    @Autowired
    private ParticipationService participationService;
    
    @GetMapping
    public String list(Model model) {
        List<Event> upcomingEvents = eventService.findUpcomingEvents();
        List<Event> pastEvents = eventService.findPastEvents();
        model.addAttribute("upcomingEvents", upcomingEvents);
        model.addAttribute("pastEvents", pastEvents);
        return "events/list";
    }
    
    @GetMapping("/new")
    public String newEvent(Model model) {
        model.addAttribute("event", new Event());
        return "events/form";
    }
    
    @PostMapping
    public String create(@Valid @ModelAttribute Event event, 
                        BindingResult result, 
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "events/form";
        }
        
        eventService.save(event);
        redirectAttributes.addFlashAttribute("message", "イベントを作成しました");
        return "redirect:/events";
    }
    
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Optional<Event> eventOpt = eventService.findById(id);
        if (eventOpt.isEmpty()) {
            return "redirect:/events";
        }
        
        Event event = eventOpt.get();
        List<Participation> participations = participationService.findByEventId(id);
        List<Member> allMembers = memberService.findAll();
        
        model.addAttribute("event", event);
        model.addAttribute("participations", participations);
        model.addAttribute("allMembers", allMembers);
        model.addAttribute("participationStatuses", ParticipationStatus.values());
        
        return "events/detail";
    }
    
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Optional<Event> eventOpt = eventService.findById(id);
        if (eventOpt.isEmpty()) {
            return "redirect:/events";
        }
        
        model.addAttribute("event", eventOpt.get());
        return "events/form";
    }
    
    @PostMapping("/{id}")
    public String update(@PathVariable Long id, 
                        @Valid @ModelAttribute Event event, 
                        BindingResult result, 
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "events/form";
        }
        
        event.setId(id);
        eventService.save(event);
        redirectAttributes.addFlashAttribute("message", "イベントを更新しました");
        return "redirect:/events/" + id;
    }
    
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        eventService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "イベントを削除しました");
        return "redirect:/events";
    }
    
    @PostMapping("/{eventId}/participate")
    public String participate(@PathVariable Long eventId,
                            @RequestParam Long memberId,
                            @RequestParam ParticipationStatus status,
                            @RequestParam(required = false) String comment,
                            RedirectAttributes redirectAttributes) {
        try {
            Optional<Event> eventOpt = eventService.findById(eventId);
            Optional<Member> memberOpt = memberService.findById(memberId);
            
            if (eventOpt.isEmpty() || memberOpt.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "イベントまたはメンバーが見つかりません");
                return "redirect:/events/" + eventId;
            }
            
            Event event = eventOpt.get();
            Member member = memberOpt.get();
            
            Optional<Participation> existingParticipation = 
                participationService.findByEventIdAndMemberId(eventId, memberId);
            
            if (existingParticipation.isPresent()) {
                // 既存の参加記録を更新
                participationService.updateParticipation(eventId, memberId, status, comment);
                redirectAttributes.addFlashAttribute("message", "参加状況を更新しました");
            } else {
                // 新規参加記録を作成
                participationService.createParticipation(event, member, status, comment);
                redirectAttributes.addFlashAttribute("message", "参加記録を作成しました");
            }
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "エラーが発生しました: " + e.getMessage());
        }
        
        return "redirect:/events/" + eventId;
    }
}