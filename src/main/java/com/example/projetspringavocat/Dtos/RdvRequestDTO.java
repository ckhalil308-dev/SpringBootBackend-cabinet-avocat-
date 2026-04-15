package com.example.projetspringavocat.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RdvRequestDTO {
    private String motif;
    private Integer creneauId;
}

