package com.example.projetspringavocat.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreneauResSecretaire {
    private Integer id;
    private LocalDate dateJour;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private Boolean reservee;
    private String titre;
}
