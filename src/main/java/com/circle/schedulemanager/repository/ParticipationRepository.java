package com.circle.schedulemanager.repository;

import com.circle.schedulemanager.entity.Participation;
import com.circle.schedulemanager.entity.ParticipationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    
    Optional<Participation> findByEventIdAndMemberId(Long eventId, Long memberId);
    
    List<Participation> findByEventId(Long eventId);
    
    List<Participation> findByMemberId(Long memberId);
    
    @Query("SELECT p FROM Participation p WHERE p.event.id = :eventId AND p.status = :status")
    List<Participation> findByEventIdAndStatus(@Param("eventId") Long eventId, 
                                               @Param("status") ParticipationStatus status);
    
    @Query("SELECT COUNT(p) FROM Participation p WHERE p.event.id = :eventId AND p.status = 'ATTENDING'")
    long countAttendingByEventId(@Param("eventId") Long eventId);
}