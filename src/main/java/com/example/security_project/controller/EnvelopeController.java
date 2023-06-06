package com.example.security_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.crypto.KeyGenerator;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.*;

@Controller
public class EnvelopeController {
    public EnvelopeController() throws IOException {
    }

    @RequestMapping("/envelope")
    public String Envelope(){
        return "/envelope";
    }
    @PostMapping("/envelope")
    public String EnvelopeDocument(@RequestParam("data") String data,
                                   @RequestParam("secretKey") String secretKeyFileName,
                                   @RequestParam("privateKey") String privateKeyFileName,
                                   @RequestParam("publicKey") String publicKeyFileName,
                                   Model model){
        String keyAlgorithm = "RSA";
        String signAlgorithm = "SHA256withRSA";

        KeyPairGenerator keyPairGen;
        KeyGenerator keyGenerator;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // secretKey 객체 생성
        try {
            keyGenerator = KeyGenerator.getInstance("DES");
            keyGenerator.init(56);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        Key secretKey;

        // 개인키 읽어들이기
        try(FileInputStream fileInputStream = new FileInputStream(privateKeyFileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            try {
                privateKey = (PrivateKey) objectInputStream.readObject();
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 대칭키 읽어들이기
        try(FileInputStream fileInputStream = new FileInputStream(secretKeyFileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){
            try {
                secretKey = (Key) objectInputStream.readObject();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//      원본 데이터 바이트 배열에 저장
        byte[] bufferData = data.getBytes();
        return "전자봉투 생성이 완료되었습니다";
    }



    @GetMapping("/home")
    public String redirectToHome() {
        return "redirect:/home.html"; // "/home.html"로 리다이렉트
    }
}
