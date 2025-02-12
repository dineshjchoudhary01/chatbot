package com.project.chatbot.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name="chat_history")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 1000) // Increase the length for aiResponse
    private String userQuestion;
    @Column(length = 100000) // Increase the length for aiResponse
    private String aiResponse;
    @ManyToOne
    @JoinColumn(name="user_id")
    private AppUser user;

    public Chat(Long id, String userQuestion, String aiResponse, AppUser user) {
        this.id = id;
        this.userQuestion = userQuestion;
        this.aiResponse = aiResponse;
        this.user = user;
    }

    public Chat() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserQuestion() {
        return userQuestion;
    }

    public void setUserQuestion(String userQuestion) {
        this.userQuestion = userQuestion;
    }

    public String getAiResponse() {
        return aiResponse;
    }

    public void setAiResponse(String aiResponse) {
        this.aiResponse = aiResponse;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}
