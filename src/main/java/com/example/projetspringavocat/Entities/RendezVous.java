package com.example.projetspringavocat.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RendezVous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String motif;
    private String message;
    @Enumerated(EnumType.STRING)
    private StatusRdv status;
    @Column(updatable = false)
    private LocalDateTime dateCreation=LocalDateTime.now();
    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private User client;
    @ManyToOne
    @JoinColumn(name = "creneau_id", unique = true)
    private Creneau creneau;





}
