package com.circle.schedulemanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "events")
public class Event {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "イベント名は必須です")
    @Size(max = 100, message = "イベント名は100文字以内で入力してください")
    @Column(nullable = false, length = 100)
    private String title;
    
    @Size(max = 500, message = "説明は500文字以内で入力してください")
    @Column(length = 500)
    private String description;
    
    @NotNull(message = "開始日時は必須です")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(nullable = false)
    private LocalDateTime startDateTime;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endDateTime;
    
    @Size(max = 200, message = "場所は200文字以内で入力してください")
    @Column(length = 200)
    private String location;
    
    @Column(nullable = false)
    private Integer maxParticipants = 0;
    
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Participation> participations;
    
    // コンストラクタ
    public Event() {}
    
    public Event(String title, String description, LocalDateTime startDateTime, 
                 LocalDateTime endDateTime, String location, Integer maxParticipants) {
        this.title = title;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.location = location;
        this.maxParticipants = maxParticipants;
    }
    
    // ゲッター・セッター
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }
    
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }
    
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }
    
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public Integer getMaxParticipants() {
        return maxParticipants;
    }
    
    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public List<Participation> getParticipations() {
        return participations;
    }
    
    public void setParticipations(List<Participation> participations) {
        this.participations = participations;
    }
    
    // 参加者数を取得
    public long getParticipantCount() {
        return participations != null ? 
            participations.stream().filter(p -> p.getStatus() == ParticipationStatus.ATTENDING).count() : 0;
    }
    
    // 参加可能かチェック
    public boolean canParticipate() {
        return maxParticipants == 0 || getParticipantCount() < maxParticipants;
    }
}