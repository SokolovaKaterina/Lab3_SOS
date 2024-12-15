package com.example.demo.controller;

import com.example.demo.domain.*;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registrationUser")
    public String registrationUser() {
        return "registrationUser";
    }

    @GetMapping("/registrationAdmin")
    public String registrationAdmin() {
        return "registrationAdmin";
    }

    @PostMapping("/registrationUser")
    public String addUser(User user, Map<String, Object> model) {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null) {
            model.put("message", "User exists!");
            return "registrationUser";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        userRepository.save(user);
        return "redirect:/login";
    }
    @PostMapping("/registrationAdmin")
    public String addAdmin(User admin, Map<String, Object> model) {
        User userFromDb = userRepository.findByUsername(admin.getUsername());
        if (userFromDb != null) {
            model.put("message", "User exists!");
            return "registrationAdmin";
        }
        admin.setActive(true);
        admin.setRoles(Collections.singleton(Role.ROLE_ADMIN));
        userRepository.save(admin);
        return "redirect:/login";
    }
}
