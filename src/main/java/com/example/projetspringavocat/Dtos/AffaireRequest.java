package com.example.projetspringavocat.Dtos;

import com.example.projetspringavocat.Entities.StatutAffaire;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AffaireRequest {
    private String titre;
    private String description;
    private String numeroDossier;
    private LocalDate dateOuverture;
    private StatutAffaire statut;
    private int clientId;

    
}

//record

