package com.example.security_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;

@Controller
public class ValidateController {
//    @RequestMapping("validate")
//    public String Validate(){
//        return "/validate";
//    }
    @PostMapping("validate")
    public String ValidateDocument(@RequestParam("data") String data,
                                   @RequestParam("secretKey") String secretKeyFileName,
                                   @RequestParam("publicKey") String publicKeyFileName,
                                   @RequestParam("validateFileName") String validateFileName,
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

        PublicKey publicKey;
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

//        공개키 읽어들이기
        try (FileInputStream fileInputStream = new FileInputStream(publicKeyFileName);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            publicKey = (PublicKey) objectInputStream.readObject();
//        서명 검증
            signature_verify = Signature.getInstance(signAlgorithm);
            signature_verify.initVerify(publicKey);
            signature_verify.update(bufferData);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
//        대칭키 읽어들이기
        try (FileInputStream fileInputStream = new FileInputStream(secretKeyFileName);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            secretKey = (Key) objectInputStream.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        byte[] signatureBuffer = new byte[128];

//        cipher 객체
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

//      서명 정보 출력하기(복호화 후 출력)
        try (FileInputStream fis = new FileInputStream(validateFileName);
             CipherInputStream cis = new CipherInputStream(fis, cipher);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[128];
            int bytesRead;
            while ((bytesRead = cis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            byte[] decryptedData = baos.toByteArray();
            //        서명 검증하기
            try {
                boolean result = signature_verify.verify(buffer);
//                System.out.println("서명 검증 결과: " + result);
                model.addAttribute("verificationResult", result);
                return "validate";
            } catch (SignatureException e) {
                throw new RuntimeException(e);
            }

        }


    }

    private void saveSignatureFile ( byte[] signature, String fileName) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(signature);
        }
    }
//    @GetMapping("/home")
//    public String redirectToHome() {
//        return "redirect:/home.html"; // "/home.html"로 리다이렉트
//    }
}
