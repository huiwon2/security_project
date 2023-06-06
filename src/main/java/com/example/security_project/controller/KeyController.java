package com.example.security_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

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
//        공개키, 개인키 생성하기
//        대칭키 생성하기

        return "키 생성이 완료되었습니다.";
    }

    private void saveKeyToFile(Object key, String fileName) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(key);
        }
    }
    @GetMapping("/home")
    public String redirectToHome() {
        return "redirect:/home.html"; // "/home.html"로 리다이렉트
    }
}
