package com.example.projetspringavocat.Repositories;

import com.example.projetspringavocat.Entities.Affaire;
import com.example.projetspringavocat.Entities.StatutAffaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AffaireRepository extends JpaRepository<Affaire, Long> {
    Optional<Affaire> findAffaireByNumeroDossier(String numeroDossier);
    List<Affaire> findAffaireByClientId(Integer clientId);
    Page<Affaire> findByStatut(StatutAffaire statut, Pageable pageable);
    long countByStatut(StatutAffaire statut);
    long countByStatutIn(List<StatutAffaire> statuts);
}
