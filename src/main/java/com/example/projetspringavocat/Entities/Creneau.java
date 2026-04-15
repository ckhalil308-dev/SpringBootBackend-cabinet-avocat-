package com.example.projetspringavocat.Entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Creneau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    @Column(nullable = false)
    private LocalDate dateJour;

    @Column(nullable = false)

    private LocalTime heureDebut;
    @Column(nullable = false)

    private LocalTime heureFin;
    private String Titre;

    @Column(nullable = false)

    private boolean reserve = false;

    @OneToMany(mappedBy = "creneau")

     private List<RendezVous> rendezVous;
}
