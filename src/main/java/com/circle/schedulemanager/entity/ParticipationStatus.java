package com.circle.schedulemanager.entity;

public enum ParticipationStatus {
    PENDING("未回答"),
    ATTENDING("参加"),
    NOT_ATTENDING("不参加"),
    MAYBE("未定");
    
    private final String displayName;
    
    ParticipationStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}