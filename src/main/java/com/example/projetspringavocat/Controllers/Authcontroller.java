package com.example.projetspringavocat.Controllers;

import com.example.projetspringavocat.Dtos.*;
import com.example.projetspringavocat.Entities.User;
import com.example.projetspringavocat.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class Authcontroller {
    @Autowired
    private AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse>login( @RequestBody  LoginRequest user){
           AuthResponse authResponse = authService.login(user);
           return ResponseEntity.ok(authResponse);

    }
    @PostMapping("/register")
    public ResponseEntity<ClientResponse> register( @RequestBody ClientRegisterRequest clientRequest){
         User u= authService.register(ClientMapper.toUser(clientRequest));
         return ResponseEntity.ok(ClientMapper.toResponse(u) );

    }

}
