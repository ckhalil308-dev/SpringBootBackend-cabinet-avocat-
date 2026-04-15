package com.example.projetspringavocat.Dtos;

import com.example.projetspringavocat.Entities.StatutAffaire;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AffaireResponse {
    private Long id;
    private String clientNom;
    private String clientPrenom;
    private String titre;
    private String description;
    private String numeroDossier;
    private LocalDate dateOuverture;
    private StatutAffaire statut;
    private Long clientId;
    private List<Long> audienceIds;
    private List<Long> documentIds;




}

