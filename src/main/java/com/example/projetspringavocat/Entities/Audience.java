package com.example.projetspringavocat.Entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Audience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dateAudience;

    @Column(nullable = false)
    private String tribunal;

    private String salle;

    private String remarques;

    @ManyToOne(optional = false)
    @JoinColumn(name = "affaire_id", nullable = false)
    private Affaire affaire;
}