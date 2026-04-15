package com.example.projetspringavocat.Dtos;

import com.example.projetspringavocat.Entities.RendezVous;

public class RdvMapper {
    public static RdvResponseDTO toResponse(RendezVous rendezVous) {
        return new RdvResponseDTO(
                rendezVous.getId(),
                rendezVous.getMotif(),
                rendezVous.getMessage(),
                rendezVous.getStatus(),
                rendezVous.getDateCreation(),
                rendezVous.getClient() != null ? rendezVous.getClient().getId() : null,
                rendezVous.getClient() != null ? rendezVous.getClient().getNom() : null,
                rendezVous.getClient() != null ? rendezVous.getClient().getPrenom() : null,
                rendezVous.getCreneau() != null ? rendezVous.getCreneau().getId() : null,
                rendezVous.getCreneau() != null ? rendezVous.getCreneau().getDateJour() : null,
                rendezVous.getCreneau() != null ? rendezVous.getCreneau().getHeureDebut() : null,
                rendezVous.getCreneau() != null ? rendezVous.getCreneau().getHeureFin() : null,
                rendezVous.getCreneau() != null && rendezVous.getCreneau().isReserve(),
                rendezVous.getCreneau() != null ? rendezVous.getCreneau().getTitre() : null
        );
    }
}

