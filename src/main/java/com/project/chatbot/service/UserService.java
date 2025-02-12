package com.project.chatbot.service;

import com.project.chatbot.entity.AppUser;
import com.project.chatbot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public AppUser registerUser(String username, String password){
        AppUser user=new AppUser();
        user.setUsername(username);
        user.setPassword(password);
        return userRepository.save(user);
    }

    public AppUser loginUser(String username, String password){
        return userRepository.findByUsername(username)
                .filter(user -> user.getPassword().equals(password))
                .orElseThrow(() -> new RuntimeException("Invalid Credentials"));
    }
}
