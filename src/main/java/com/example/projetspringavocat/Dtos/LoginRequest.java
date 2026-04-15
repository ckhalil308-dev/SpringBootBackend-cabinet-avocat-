package com.example.projetspringavocat.Dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    public String email;
    public String password;
}
