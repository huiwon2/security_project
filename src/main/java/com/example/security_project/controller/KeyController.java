package com.example.security_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class KeyController {
    @GetMapping("/")
    public String key() {
        return "/key"; // key.html을 렌더링하여 응답으로 보냄
    }
    @PostMapping("/key")
    public String generateKeys(@RequestParam("privateKey") String privateKey,
                               @RequestParam("publicKey") String publicKey,
                               @RequestParam("secretKey") String secretKey,
                               Model model){


    }

    @GetMapping("/home")
    public String redirectToHome() {
        return "redirect:/home.html"; // "/home.html"로 리다이렉트
    }
}
