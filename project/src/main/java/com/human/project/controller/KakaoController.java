package com.human.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class KakaoController {


    // http://localhost:8080/login/oauth2/code/kakao
    @GetMapping("/login/oauth2/code/kakao")
    public ResponseEntity<String> getMethodName(@RequestParam String param) {
        
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }
    
    
}
