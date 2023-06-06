package com.example.security_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class ValidateController {
    @RequestMapping("/validate")
    public String Validate(){
        return "/validate";
    }
}
