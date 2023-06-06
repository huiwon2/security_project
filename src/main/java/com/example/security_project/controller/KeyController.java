package com.example.security_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.crypto.KeyGenerator;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.*;

@Controller
public class KeyController {
//    @GetMapping("/key")
//    public String key() {
//        return "/key"; // key.html을 렌더링하여 응답으로 보냄
//    }
    @PostMapping("key")
    public String generateKeys(@RequestParam("secretKey") String secretKeyFileName,
                               @RequestParam("privateKey") String privateKeyFileName,
                               @RequestParam("publicKey") String publicKeyFileName,
                               Model model){
//        대칭키 생성하기
        try{
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            keyGenerator.init(56);
            Key secretKey = keyGenerator.generateKey();
//         대칭키 저장하기
            saveKeyToFile(secretKey, secretKeyFileName);

        }catch (NoSuchAlgorithmException | IOException e){
            e.printStackTrace();
        }
//        공개키, 개인키 생성하기
        try {
            // KeyPairGenerator를 사용하여 키 생성
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(1024);
            KeyPair keyPair = keyPairGen.generateKeyPair();

            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

//          개인키 저장
            saveKeyToFile(privateKey, privateKeyFileName);

//          공개키 저장
            saveKeyToFile(publicKey, publicKeyFileName);

            model.addAttribute("Key", "KeyGenerated");
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }

        return "key";
    }

    private void saveKeyToFile(Object key, String fileName) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(key);
        }
    }
//    @GetMapping("/home")
//    public String redirectToHome() {
//        return "redirect:/home.html"; // "/home.html"로 리다이렉트
//    }
}
