package com.circle.schedulemanager.controller;

import com.circle.schedulemanager.entity.Member;
import com.circle.schedulemanager.entity.Participation;
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
@RequestMapping("/members")
public class MemberController {
    
    @Autowired
    private MemberService memberService;
    
    @Autowired
    private ParticipationService participationService;
    
    @GetMapping
    public String list(Model model) {
        List<Member> members = memberService.findAll();
        model.addAttribute("members", members);
        return "members/list";
    }
    
    @GetMapping("/new")
    public String newMember(Model model) {
        model.addAttribute("member", new Member());
        return "members/form";
    }
    
    @PostMapping
    public String create(@Valid @ModelAttribute Member member, 
                        BindingResult result, 
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "members/form";
        }
        
        if (memberService.existsByEmail(member.getEmail())) {
            result.rejectValue("email", "error.member", "このメールアドレスは既に登録されています");
            return "members/form";
        }
        
        memberService.save(member);
        redirectAttributes.addFlashAttribute("message", "メンバーを登録しました");
        return "redirect:/members";
    }
    
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Optional<Member> memberOpt = memberService.findById(id);
        if (memberOpt.isEmpty()) {
            return "redirect:/members";
        }
        
        Member member = memberOpt.get();
        List<Participation> participations = participationService.findByMemberId(id);
        
        model.addAttribute("member", member);
        model.addAttribute("participations", participations);
        
        return "members/detail";
    }
    
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Optional<Member> memberOpt = memberService.findById(id);
        if (memberOpt.isEmpty()) {
            return "redirect:/members";
        }
        
        model.addAttribute("member", memberOpt.get());
        return "members/form";
    }
    
    @PostMapping("/{id}")
    public String update(@PathVariable Long id, 
                        @Valid @ModelAttribute Member member, 
                        BindingResult result, 
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "members/form";
        }
        
        if (!memberService.isEmailAvailable(member.getEmail(), id)) {
            result.rejectValue("email", "error.member", "このメールアドレスは既に登録されています");
            return "members/form";
        }
        
        member.setId(id);
        memberService.save(member);
        redirectAttributes.addFlashAttribute("message", "メンバー情報を更新しました");
        return "redirect:/members/" + id;
    }
    
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        memberService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "メンバーを削除しました");
        return "redirect:/members";
    }
}