package com.example.projetspringavocat.Dtos;

import com.example.projetspringavocat.Entities.Affaire;
import com.example.projetspringavocat.Entities.Audience;

public class AudienceMapper {
    public static Audience toEntity(AudienceRequest request, Affaire affaire) {
        Audience audience = new Audience();
        audience.setDateAudience(request.getDateAudience());
        audience.setTribunal(request.getTribunal());
        audience.setSalle(request.getSalle());
        audience.setRemarques(request.getRemarques());
        audience.setAffaire(affaire);
        return audience;
    }

    public static void updateEntityFromRequest(Audience audience, AudienceRequest request, Affaire affaire) {
        audience.setDateAudience(request.getDateAudience());
        audience.setTribunal(request.getTribunal());
        audience.setSalle(request.getSalle());
        audience.setRemarques(request.getRemarques());
        if (affaire != null) {
            audience.setAffaire(affaire);
        }
    }

    public static AudienceResponse toResponse(Audience audience) {
        Long affaireId = audience.getAffaire() != null ? audience.getAffaire().getId() : null;
        String affaireTitre = audience.getAffaire() != null ? audience.getAffaire().getTitre() : null;
        Integer clientId = audience.getAffaire() != null && audience.getAffaire().getClient() != null
                ? audience.getAffaire().getClient().getId()
                : null;
        String clientNom = audience.getAffaire() != null && audience.getAffaire().getClient() != null
                ? audience.getAffaire().getClient().getNom()
                : null;
        String clientPrenom = audience.getAffaire() != null && audience.getAffaire().getClient() != null
                ? audience.getAffaire().getClient().getPrenom()
                : null;

        return new AudienceResponse(
                audience.getId(),
                audience.getDateAudience(),
                audience.getTribunal(),
                audience.getSalle(),
                audience.getRemarques(),
                affaireId,
                affaireTitre,
                clientId,
                clientNom,
                clientPrenom
        );
    }
}

