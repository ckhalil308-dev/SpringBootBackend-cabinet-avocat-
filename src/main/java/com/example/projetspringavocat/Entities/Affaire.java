package com.example.projetspringavocat.Entities;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Affaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(length = 1000)

    private String description;

    @Column(nullable = false, unique = true)

    private String numeroDossier;
    @Column(nullable = false)

    private LocalDate dateOuverture = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private StatutAffaire statut = StatutAffaire.EN_COURS;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    @OneToMany(mappedBy = "affaire", cascade = CascadeType.ALL)
    private List<Audience> audiences;

    @OneToMany(mappedBy = "affaire", cascade = CascadeType.ALL)
    private List<Document> documents;


}