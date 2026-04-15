package com.example.projetspringavocat.Dtos;

import com.example.projetspringavocat.Entities.RendezVous;
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
public class CreneauRequest {
    private LocalDate dateJour;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    public String titre;


}
