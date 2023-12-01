package com.walker.learningspringsecurity2.controller;

import com.walker.learningspringsecurity2.entity.User;
import com.walker.learningspringsecurity2.security.AuthToken;
import com.walker.learningspringsecurity2.security.TokenValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @GetMapping("/free")
    public String sayFreeHello(){
        return "Este é um endpoint liberado pela nossa API";
    }

    @GetMapping("/auth")
    public String sayAuthHello(){
        return "Este é um endpoint que precisa de autenticação";
    }

    @PostMapping("/login")
    public ResponseEntity<AuthToken> executeLogin(@RequestBody User user){
        if (user.getLogin().equals("walker") && user.getPassword().equals("admin123456")){
            return ResponseEntity.ok(TokenValidation.encodeToken(user));
        }
        return ResponseEntity.status(403).build();
    }
}
