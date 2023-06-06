package com.example.security_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KeyController {
    @GetMapping("/")
    public String key() {
        return "/key"; // home.html을 렌더링하여 응답으로 보냄
    }

    @GetMapping("/home")
    public String redirectToHome() {
        return "redirect:/home.html"; // "/home.html"로 리다이렉트
    }
}
