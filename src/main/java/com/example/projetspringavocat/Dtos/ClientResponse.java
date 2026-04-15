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
public class ClientResponse {
    private Integer id;
    private String nom;
    private String prenom;
    private String email;
    private int numTelephone;
    private Role role;
}

