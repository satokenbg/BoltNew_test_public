package com.circle.schedulemanager.service;

import com.circle.schedulemanager.entity.Event;
import com.circle.schedulemanager.entity.Member;
import com.circle.schedulemanager.entity.Participation;
import com.circle.schedulemanager.entity.ParticipationStatus;
import com.circle.schedulemanager.repository.ParticipationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ParticipationService {
    
    @Autowired
    private ParticipationRepository participationRepository;
    
    public List<Participation> findByEventId(Long eventId) {
        return participationRepository.findByEventId(eventId);
    }
    
    public List<Participation> findByMemberId(Long memberId) {
        return participationRepository.findByMemberId(memberId);
    }
    
    public Optional<Participation> findByEventIdAndMemberId(Long eventId, Long memberId) {
        return participationRepository.findByEventIdAndMemberId(eventId, memberId);
    }
    
    public List<Participation> findByEventIdAndStatus(Long eventId, ParticipationStatus status) {
        return participationRepository.findByEventIdAndStatus(eventId, status);
    }
    
    public long countAttendingByEventId(Long eventId) {
        return participationRepository.countAttendingByEventId(eventId);
    }
    
    public Participation save(Participation participation) {
        return participationRepository.save(participation);
    }
    
    public Participation updateParticipation(Long eventId, Long memberId, 
                                           ParticipationStatus status, String comment) {
        Optional<Participation> existing = findByEventIdAndMemberId(eventId, memberId);
        
        if (existing.isPresent()) {
            Participation participation = existing.get();
            participation.setStatus(status);
            participation.setComment(comment);
            return save(participation);
        } else {
            // 新規作成の場合は、EventとMemberのエンティティが必要
            throw new IllegalArgumentException("参加記録が見つかりません");
        }
    }
    
    public Participation createParticipation(Event event, Member member, 
                                           ParticipationStatus status, String comment) {
        // 既存の参加記録をチェック
        Optional<Participation> existing = findByEventIdAndMemberId(event.getId(), member.getId());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("既に参加記録が存在します");
        }
        
        Participation participation = new Participation(event, member, status, comment);
        return save(participation);
    }
    
    public void deleteById(Long id) {
        participationRepository.deleteById(id);
    }
}