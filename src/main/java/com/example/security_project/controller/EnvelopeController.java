package com.example.security_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EnvelopeController {
    @RequestMapping("/envelope")
    public String Envelope(){
        return "/envelope";
    }
    @PostMapping("/envelope")
    public String EnvelopeDocument(@RequestParam("data") String secretKeyFileName,
                                   @RequestParam("privateKey") String privateKeyFileName,
                                   @RequestParam("publicKey") String publicKeyFileName,
                                   Model model){

        return "전자봉투 생성이 완료되었습니다";
    }
    @GetMapping("/home")
    public String redirectToHome() {
        return "redirect:/home.html"; // "/home.html"로 리다이렉트
    }
}
