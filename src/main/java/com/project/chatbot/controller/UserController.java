package com.project.chatbot.controller;

import com.project.chatbot.entity.AppUser;
import com.project.chatbot.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5174")  // Apply to all methods in this controller
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public AppUser registerUser(@RequestBody AppUser user){
        return userService.registerUser(user.getUsername(),user.getPassword());
    }

    @PostMapping("/login")
    public AppUser loginUser(@RequestBody AppUser user, HttpSession session) {
        AppUser loggedInUser = userService.loginUser(user.getUsername(), user.getPassword());

        // Store user information in session (only userId here for simplicity)
        session.setAttribute("userId", loggedInUser.getId());
        return loggedInUser;
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        // Invalidate the session to log out
        session.invalidate();
        return "Logged out successfully!";
    }
}
