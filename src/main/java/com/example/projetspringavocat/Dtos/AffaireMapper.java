package com.example.projetspringavocat.Dtos;

import com.example.projetspringavocat.Entities.Affaire;
import com.example.projetspringavocat.Entities.Audience;
import com.example.projetspringavocat.Entities.Document;
import com.example.projetspringavocat.Entities.User;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AffaireMapper {
    public static Affaire toEntity(AffaireRequest request, User client) {
        Affaire affaire = new Affaire();
        affaire.setTitre(request.getTitre());
        if(affaire.getDescription()!=null){
            affaire.setDescription(request.getDescription());
        }
        affaire.setNumeroDossier(request.getNumeroDossier());
        affaire.setDateOuverture(request.getDateOuverture());
        affaire.setStatut(request.getStatut());
        affaire.setClient(client);
        return affaire;
    }

    public static void updateEntityFromRequest(Affaire affaire, AffaireRequest request, User client) {
        affaire.setTitre(request.getTitre());
        affaire.setDescription(request.getDescription());
        affaire.setNumeroDossier(request.getNumeroDossier());
        if (request.getDateOuverture() != null) {
            affaire.setDateOuverture(request.getDateOuverture());
        }
        if (request.getStatut() != null) {
            affaire.setStatut(request.getStatut());
        }
        if (client != null) {
            affaire.setClient(client);
        }
    }

    public static AffaireResponse toResponse(Affaire affaire) {
        Long clientId = affaire.getClient() != null ? Long.valueOf(affaire.getClient().getId()) : null;
        List<Long> audienceIds = affaire.getAudiences() != null
                ? affaire.getAudiences().stream().map(Audience::getId).collect(Collectors.toList())
                : Collections.emptyList();
        List<Long> documentIds = affaire.getDocuments() != null
                ? affaire.getDocuments().stream().map(Document::getId).collect(Collectors.toList())
                : Collections.emptyList();

        return new AffaireResponse(
                affaire.getId(),
                affaire.getClient().getNom(),
                affaire.getClient().getPrenom(),
                affaire.getTitre(),
                affaire.getDescription(),
                affaire.getNumeroDossier(),
                affaire.getDateOuverture(),
                affaire.getStatut(),
                clientId,
                audienceIds,
                documentIds
        );
    }


}
