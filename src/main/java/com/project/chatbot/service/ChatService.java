package com.project.chatbot.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.chatbot.entity.Chat;
import com.project.chatbot.entity.AppUser;
import com.project.chatbot.repository.ChatRepository;
import com.project.chatbot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${api.key}")
    private String geminiApiKey;

    @Value("${api.url}")
    private String apiUrl;

    private final ObjectMapper objectMapper = new ObjectMapper();


    public Chat askQuestion(long userId, String question){
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String url="" + geminiApiKey;

        // Proper JSON request body
        String requestBody = String.format(
                "{ \"contents\": [{ \"parts\": [{ \"text\": \"%s\" }] }] }",
                question
        );

        // Set Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create HTTP entity with headers and body
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Initialize RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        String response = restTemplate.postForObject(url, requestEntity, String.class);

        String aiResponseText = extractTextFromResponse(response);

        Chat chat=new Chat();
        chat.setUserQuestion(question);
        chat.setAiResponse(aiResponseText);
        chat.setUser(new AppUser(userId));

        return chatRepository.save(chat);
    }

    public List<Chat> getChatHistory(Long userId){
        return chatRepository.findByUserId(userId);
    }

    // Extract AI response text
    private String extractTextFromResponse(String response){
        try{
            JsonNode root=objectMapper.readTree(response);
            JsonNode candidates=root.path("candidates");

            if(candidates.isArray() && candidates.size()>0){
                JsonNode firstCandidate=candidates.get(0);
                JsonNode content=firstCandidate.path("content");
                JsonNode parts=content.path("parts");

                if(parts.isArray() && parts.size() >0){
                    return parts.get(0).path("text").asText();
                }
            }
        }catch (Exception e){
            return " Error processing AI response: "+e.getLocalizedMessage();
        }
        return "No response form AI";
    }
}
