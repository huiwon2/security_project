package com.example.security_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class ValidateController {
    @RequestMapping("/validate")
    public String Validate(){
        return "/validate";
    }

    @GetMapping("/home")
    public String redirectToHome() {
        return "redirect:/home.html"; // "/home.html"로 리다이렉트
    }
}
