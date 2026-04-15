package com.example.projetspringavocat.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AudienceResponse {
    private Long id;
    LocalDateTime dateAudience;
    private String tribunal;
    private String salle;
    private String remarques;
    private Long affaireId;
    private String affaireTitre;
    private Integer clientId;
    private String clientNom;
    private String clientPrenom;



}
