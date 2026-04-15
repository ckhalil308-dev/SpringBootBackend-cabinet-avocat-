package com.example.projetspringavocat.Services;

import com.example.projetspringavocat.Dtos.AudienceRequest;
import com.example.projetspringavocat.Entities.Audience;

import java.time.LocalDateTime;
import java.util.List;

public interface AudienceService {
    Audience createAudience(AudienceRequest request);
    List<Audience> getAudiencesByClient();
    List<Audience> getAudiencesByAffaireId(Long affaireId);
    Audience modifierAudience(Long id, AudienceRequest request);
    void supprimerAudience(Long id);
    Audience reporterAudience(Long id, LocalDateTime nouvelleDate, String motif);
}

