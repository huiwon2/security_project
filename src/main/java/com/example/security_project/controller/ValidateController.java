package com.example.security_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ValidateController {
    @RequestMapping("/validate")
    public String Validate(){
        return "/validate";
    }
    @PostMapping("/validate")
    public String ValidateDocument(@RequestParam("data") String data,
                                   @RequestParam("secretKey") String secretKeyFileName,
                                   @RequestParam("privateKey") String privateKeyFileName,
                                   @RequestParam("envelopeFileName") String envelopeFileName,
                                   Model model){

    }
    @GetMapping("/home")
    public String redirectToHome() {
        return "redirect:/home.html"; // "/home.html"로 리다이렉트
    }
}
