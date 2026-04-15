package com.example.projetspringavocat.Dtos;

import com.example.projetspringavocat.Entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String Token;
    private String nom;
    private String role ;
}

