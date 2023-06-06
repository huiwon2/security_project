package com.example.security_project.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;

@Controller
public class MainController {
    @GetMapping("/")
    public String home() {
        return "home.html"; // home.html을 렌더링하여 응답으로 보냄
    }

    @GetMapping("/key")
    public String redirectToKeyPage() {
        return "redirect:/key.html"; // "/key.html"로 리다이렉트
    }

    @GetMapping("/envelope")
    public String redirectToEnvelopePage() {
        return "redirect:/envelope.html"; // "/envelope.html"로 리다이렉트
    }

    @GetMapping("/validate")
    public String redirectToValidatePage() {
        return "redirect:/validate.html"; // "/validate.html"로 리다이렉트
    }
    @GetMapping("/static/cat1.png")
    @ResponseBody
    public ResponseEntity<Resource> getCat1Image() throws IOException {
        ClassPathResource imageResource = new ClassPathResource("static/cat1.png");
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body((Resource) new InputStreamResource(imageResource.getInputStream()));
    }

    @GetMapping("/static/cat2.png")
    @ResponseBody
    public ResponseEntity<Resource> getCat2Image() throws IOException {
        ClassPathResource imageResource = new ClassPathResource("static/cat2.png");
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body((Resource) new InputStreamResource(imageResource.getInputStream()));
    }

    @GetMapping("/static/cat3.png")
    @ResponseBody
    public ResponseEntity<Resource> getCat3Image() throws IOException {
        ClassPathResource imageResource = new ClassPathResource("static/cat3.png");
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body((Resource) new InputStreamResource(imageResource.getInputStream()));
    }
}
