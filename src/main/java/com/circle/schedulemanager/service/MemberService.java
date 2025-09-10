package com.circle.schedulemanager.service;

import com.circle.schedulemanager.entity.Member;
import com.circle.schedulemanager.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberService {
    
    @Autowired
    private MemberRepository memberRepository;
    
    public List<Member> findAll() {
        return memberRepository.findAll();
    }
    
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }
    
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
    
    public Member save(Member member) {
        return memberRepository.save(member);
    }
    
    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }
    
    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }
    
    public boolean isEmailAvailable(String email, Long excludeId) {
        Optional<Member> existing = memberRepository.findByEmail(email);
        return existing.isEmpty() || existing.get().getId().equals(excludeId);
    }
}