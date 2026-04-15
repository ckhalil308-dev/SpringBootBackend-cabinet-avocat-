package com.example.projetspringavocat.Dtos;

import com.example.projetspringavocat.Entities.StatusRdv;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RdvResponseDTO {
    private Integer id;
    private String motif;
    private String message;
    private StatusRdv status;
    private LocalDateTime dateCreation;
    private Integer clientId;
    private String clientNom;
    private String clientPrenom;
    private Integer creneauId;
    private LocalDate creneauDateJour;
    private LocalTime creneauHeureDebut;
    private LocalTime creneauHeureFin;
    private boolean creneauReserve;
    private String creneauTitre;
}

