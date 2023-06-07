package com.example.security_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;

@Controller
public class EnvelopeController {
    public EnvelopeController() throws IOException {
    }

    @RequestMapping("envelope")
    public String Envelope(){
        return "/envelope";
    }
    @PostMapping("envelope")
    public String EnvelopeDocument(@RequestParam("data") String data,
                                   @RequestParam("secretKey") String secretKeyFileName,
                                   @RequestParam("privateKey") String privateKeyFileName,
                                   @RequestParam("envelopeFileName") String envelopeFileName,
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

        //원본 데이터 바이트 배열에 저장
        byte[] bufferData = data.getBytes();

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

        // cipher 객체
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        // signature 생성하기
        Signature signature;
        byte[] sign;
        try {
            signature = Signature.getInstance(signAlgorithm);
            signature.initSign(privateKey);
            signature.update(bufferData);
            sign = signature.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e);
        }
        // 서명을 대칭키로 암호화하기
        try (FileOutputStream bos = new FileOutputStream(envelopeFileName);
             CipherOutputStream cos = new CipherOutputStream(bos, cipher)) {
            cos.write(sign);
            cos.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("message", "전자봉투 생성이 완료되었습니다");
        return "envelope";
    }

//    @GetMapping("/home")
//    public String redirectToHome() {
//        return "redirect:/home.html"; // "/home.html"로 리다이렉트
//    }
}
