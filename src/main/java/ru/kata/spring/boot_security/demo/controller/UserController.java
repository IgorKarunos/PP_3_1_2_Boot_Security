package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;

import java.security.Principal;

@Controller
public class UserController {
    @GetMapping("/user")
    public String userPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "user";
    }
}