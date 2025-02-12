package com.project.chatbot.controller;

import com.project.chatbot.entity.Chat;
import com.project.chatbot.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5174")  // Apply to all methods in this controller
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/ask")
    public Chat askQuestion(@RequestBody Chat chatRequest){
        return chatService.askQuestion(chatRequest.getUser().getId(), chatRequest.getUserQuestion());
    }

    @GetMapping("/history/{userId}")
    public List<Chat> getChatHistory(@PathVariable Long userId){
        return chatService.getChatHistory(userId);
    }

}
