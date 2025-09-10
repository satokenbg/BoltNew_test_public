package com.circle.schedulemanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "members")
public class Member {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "名前は必須です")
    @Size(max = 50, message = "名前は50文字以内で入力してください")
    @Column(nullable = false, length = 50)
    private String name;
    
    @Email(message = "正しいメールアドレスを入力してください")
    @Size(max = 100, message = "メールアドレスは100文字以内で入力してください")
    @Column(unique = true, length = 100)
    private String email;
    
    @Size(max = 20, message = "電話番号は20文字以内で入力してください")
    @Column(length = 20)
    private String phone;
    
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Participation> participations;
    
    // コンストラクタ
    public Member() {}
    
    public Member(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    
    // ゲッター・セッター
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public List<Participation> getParticipations() {
        return participations;
    }
    
    public void setParticipations(List<Participation> participations) {
        this.participations = participations;
    }
}