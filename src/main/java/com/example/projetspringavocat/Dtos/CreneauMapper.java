package com.example.projetspringavocat.Dtos;

import com.example.projetspringavocat.Entities.Creneau;

public class CreneauMapper {

    public static CreneauResClient toClientDto(Creneau c) {
        CreneauResClient dto = new CreneauResClient();
        dto.setId(c.getId());
        dto.setDateJour(c.getDateJour());
        dto.setHeureDebut(c.getHeureDebut());
        dto.setHeureFin(c.getHeureFin());
        dto.setReservee(c.isReserve());
        return dto;
    }

    public static CreneauResSecretaire toSecretaireDto(Creneau c) {
        CreneauResSecretaire dto = new CreneauResSecretaire();
        dto.setId(c.getId());
        dto.setDateJour(c.getDateJour());
        dto.setHeureDebut(c.getHeureDebut());
        dto.setHeureFin(c.getHeureFin());
        dto.setReservee(c.isReserve());
        dto.setTitre(c.getTitre());
        return dto;
    }
}

