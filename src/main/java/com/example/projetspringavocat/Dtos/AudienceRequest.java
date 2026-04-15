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
public class AudienceRequest {
    LocalDateTime dateAudience;
    private String tribunal;
    private String salle;
    private String remarques;
    private Long affaireId;

}
