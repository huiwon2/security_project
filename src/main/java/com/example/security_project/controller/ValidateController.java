package com.example.security_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.crypto.KeyGenerator;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.*;

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
                                   Model model) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
        String keyAlgorithm = "RSA";
        String signAlgorithm = "SHA256withRSA";
        String signatureName = "sig.bin";

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

        //        secretKey 객체 생성
        try {
            keyGenerator = KeyGenerator.getInstance("DES");
            keyGenerator.init(56);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        Key secretKey;

        //        bufferData => data의 byte타입으로 변환
        byte[] bufferData = data.getBytes();
//        signature 생성하기
        Signature signature;
        Signature signature_verify;// 검증 signature 객체
        byte[] sign;
        signature = Signature.getInstance(signAlgorithm);
        signature.initSign(privateKey);
        signature.update(bufferData);
        sign = signature.sign();

        saveSignatureFile(sign, signatureName);

    }
    private void saveSignatureFile(byte[] signature, String fileName) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(signature);
        }
    }
    @GetMapping("/home")
    public String redirectToHome() {
        return "redirect:/home.html"; // "/home.html"로 리다이렉트
    }
}
