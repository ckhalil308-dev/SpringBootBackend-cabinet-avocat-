package com.example.projetspringavocat.Dtos;

import com.example.projetspringavocat.Entities.Role;
import com.example.projetspringavocat.Entities.User;

public class ClientMapper {
    public static User toUser(ClientRequest request) {
        User user = new User();
        user.setNom(request.getNom());
        user.setPrenom(request.getPrenom());
        user.setEmail(request.getEmail());
        user.setNumTelephone(request.getNumTelephone());
        return user;
    }

    public static User toUser(ClientRegisterRequest request) {
        User user = new User();
        user.setNom(request.getNom());
        user.setPrenom(request.getPrenom());
        user.setEmail(request.getEmail());
        user.setNumTelephone(request.getNumTelephone());
        user.setPassword(request.getPassword());
        user.setRole(Role.CLIENT);
        return user;
    }

    public static ClientResponse toResponse(User user) {
        return new ClientResponse(
                user.getId(),
                user.getNom(),
                user.getPrenom(),
                user.getEmail(),
                user.getNumTelephone(),
                user.getRole()
        );
    }
}

