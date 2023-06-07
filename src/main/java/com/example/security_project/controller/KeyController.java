package com.example.security_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.crypto.KeyGenerator;
import java.io.*;
import java.security.*;

@Controller
public class KeyController {
//    @GetMapping("/key")
//    public String key() {
//        return "/key"; // key.html을 렌더링하여 응답으로 보냄
//    }
    @PostMapping("key")
    public String generateKeys(Model model, @RequestParam("secretKey") String secretKeyFile,
                               @RequestParam("publicKey") String publicKeyFile,
                               @RequestParam("privateKey") String privateKeyFile){
        // 대칭키 생성하기
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            keyGenerator.init(56);
            Key secretKey = keyGenerator.generateKey();
            // 대칭키 저장하기
            saveKeyToFile(secretKey, secretKeyFile);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // 공개키, 개인키 생성하기
        try {
            // KeyPairGenerator를 사용하여 키 생성
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(1024);
            KeyPair keyPair = keyPairGen.generateKeyPair();

            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            // 개인키 저장
            saveKeyToFile(privateKey, privateKeyFile);

            // 공개키 저장
            saveKeyToFile(publicKey, publicKeyFile);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        model.addAttribute("generatedKeys", true);
        model.addAttribute("secretKeyFile", secretKeyFile);
        model.addAttribute("publicKeyFile", publicKeyFile);
        model.addAttribute("privateKeyFile", privateKeyFile);

        return "home";

    }

    private void saveKeyToFile(Object key, String fileName) {

//        windows
        String filePath = "C:/Users/snowm/IdeaProjects/security_project/" + fileName;
//        mac
        String macFilePath = "/Users/ihuiwon/IdeaProjects/security_project" + fileName;


//        (코드리뷰-함부로 예외처리 어쩌구)
//        // 2. 파일이 이미 존재하는 경우 예외 처리
//        if (file.exists()) {
//            System.out.println("파일이 이미 존재합니다.");
//            return;
//        }
//        // 3. 파일에 대한 쓰기 권한이 없는 경우 예외 처리
//        if (!file.getParentFile().canWrite()) {
//            System.out.println("파일에 대한 쓰기 권한이 없습니다.");
//            return;
//        }

//        코드리뷰 : throws대신 catch로 잡기
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    @GetMapping("/home")
//    public String redirectToHome() {
//        return "redirect:/home"; // "/home.html"로 리다이렉트
//    }
}
