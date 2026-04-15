package com.example.projetspringavocat.Entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String type;

    @Column(nullable = false)

    private String cheminFichier;

    private LocalDateTime dateUpload = LocalDateTime.now();

    @ManyToOne(optional = false)

    @JoinColumn(name = "affaire_id", nullable = false)
    private Affaire affaire;
}
