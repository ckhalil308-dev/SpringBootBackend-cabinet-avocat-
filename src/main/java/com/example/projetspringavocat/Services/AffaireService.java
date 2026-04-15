package com.example.projetspringavocat.Services;

import com.example.projetspringavocat.Dtos.AffaireRequest;
import com.example.projetspringavocat.Entities.Affaire;
import com.example.projetspringavocat.Entities.StatutAffaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AffaireService {
    Affaire creeAffaire(AffaireRequest affaireRequest);
    Page<Affaire> getAll(StatutAffaire statut, Pageable pageable);
    Affaire getAffaireById(Long id);
    Affaire modifierAffaire(Long id, AffaireRequest affaireRequest);
    void supprimerAffaire(Long id);
    List<Affaire> getAffairesByClient();
    List<Affaire> getAffairesByClientId(Integer clientId);
}
