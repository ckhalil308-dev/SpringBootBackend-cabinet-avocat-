package com.example.projetspringavocat.Repositories;

import com.example.projetspringavocat.Entities.Creneau;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface CreneauRepository extends JpaRepository<Creneau, Integer> {
    List <Creneau> findByDateJourBetweenOrderByDateJourAscHeureDebutAsc(LocalDate d1 , LocalDate d2);
    List<Creneau> findByDateJourAndReserveFalseOrderByHeureDebutAsc(LocalDate dateJour);
    List<Creneau> findByDateJourOrderByHeureDebutAsc(LocalDate dateJour);
    boolean existsByDateJour(LocalDate dateJour);
    boolean existsByDateJourAndHeureDebutAndHeureFin(LocalDate dateJour, LocalTime heureDebut, LocalTime heureFin);
    java.util.Optional<Creneau> findByIdAndReserveFalse(Integer id);
}

