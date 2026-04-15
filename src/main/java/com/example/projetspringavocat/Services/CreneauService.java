package com.example.projetspringavocat.Services;

import com.example.projetspringavocat.Dtos.CreneauRequest;
import com.example.projetspringavocat.Dtos.ModifierCreneauDto;
import com.example.projetspringavocat.Entities.Creneau;

import java.time.LocalDate;
import java.util.List;

public interface CreneauService {
    void genererCreneauxJournaliers(LocalDate date);
    List<Creneau> getDisponiblesParDate(LocalDate date);
    Creneau getById(Integer id);
    Creneau bloquerCreneau(Integer id, String motif);
    List<Creneau> getPlanningSemaine(LocalDate dateReference);
    void supprimerCreneau(Integer id);
    Creneau modifierCreneau(Integer id, ModifierCreneauDto cre);
    Creneau creerCreneau(CreneauRequest request);
}