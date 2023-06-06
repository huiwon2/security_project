package com.example.security_project.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class KeyRequest {
    private String secretKey;
    private String privateKey;
    private String publicKey;

}
