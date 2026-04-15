package com.example.projetspringavocat.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private int numTelephone;

    @Column(unique = true , nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false )
    @Enumerated(EnumType.STRING)
    private Role role;

    public boolean estVerifie=false;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<RendezVous> rendezVous;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Affaire> affaires ;



}
