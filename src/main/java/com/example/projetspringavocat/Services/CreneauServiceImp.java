package com.example.projetspringavocat.Services;

import com.example.projetspringavocat.Dtos.CreneauRequest;
import com.example.projetspringavocat.Dtos.ModifierCreneauDto;
import com.example.projetspringavocat.Entities.Creneau;
import com.example.projetspringavocat.Repositories.CreneauRepository;
import com.example.projetspringavocat.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class CreneauServiceImp implements CreneauService {

    private final CreneauRepository creneauRepository;

    public CreneauServiceImp(CreneauRepository creneauRepository) {
        this.creneauRepository = creneauRepository;
    }

    @Transactional
    @Override
    public void genererCreneauxJournaliers(LocalDate date) {
        if(date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La date doit être aujourd'hui ou dans le futur");
        }
        LocalTime heureDebut = LocalTime.of(8, 0);
        LocalTime heureFin = LocalTime.of(17, 0);
        int intervalleMinutes = 60;

        LocalTime courant = heureDebut;
        List<Creneau> existants = creneauRepository.findByDateJourOrderByHeureDebutAsc(date);
        if(!existants.isEmpty()) {
            throw new ResourceNotFoundException("Des créneaux existent déjà pour cette date");
        }
        while (courant.isBefore(heureFin)) {
            Creneau creneau = new Creneau();
            creneau.setDateJour(date);
            creneau.setHeureDebut(courant);
            creneau.setHeureFin(courant.plusMinutes(intervalleMinutes));
            creneau.setReserve(false);
            creneauRepository.save(creneau);
            courant = courant.plusMinutes(intervalleMinutes);
        }
    }

    @Override
    public List<Creneau> getDisponiblesParDate(LocalDate date) {
        return creneauRepository.findByDateJourAndReserveFalseOrderByHeureDebutAsc(date);
    }

    @Override
    public Creneau getById(Integer id) {
        return creneauRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Créneau introuvable"));
    }

    @Transactional
    @Override
    public Creneau bloquerCreneau(Integer id, String motif) {
        Creneau creneau = creneauRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Créneau introuvable"));

        if (creneau.isReserve()) {
            throw new IllegalStateException("Créneau déjà réservé");
        }

        creneau.setReserve(true);
        creneau.setTitre(motif);
        return creneauRepository.save(creneau);
    }

    @Override
    public List<Creneau> getPlanningSemaine(LocalDate dateReference) {
        LocalDate lundi = dateReference.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate dimanche = lundi.plusDays(6);
        return creneauRepository.findByDateJourBetweenOrderByDateJourAscHeureDebutAsc(lundi, dimanche);
    }

    @Override
    public void supprimerCreneau(Integer id) {
        if (!creneauRepository.existsById(id)) {
            throw new ResourceNotFoundException("Créneau introuvable");
        }
        creneauRepository.deleteById(id);
    }


    @Transactional
    @Override
    public Creneau modifierCreneau(Integer id, ModifierCreneauDto cre) {
        Creneau creneau = creneauRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Créneau introuvable"));
        creneau.setReserve(cre.isReserve());
        if (cre.getMotif() != null) {
            creneau.setTitre(cre.getMotif());
        } else if (!cre.isReserve()) {
            creneau.setTitre(null);
        }
        return creneauRepository.save(creneau);
    }
    @Transactional
    @Override
    public Creneau creerCreneau(CreneauRequest request) {
        LocalDate date = request.getDateJour();
        LocalTime debut = request.getHeureDebut();
        LocalTime fin = request.getHeureFin();

        if (date == null || debut == null || fin == null) {
            throw new IllegalArgumentException("Date, heureDebut et heureFin sont obligatoires");
        }

        if (!fin.isAfter(debut)) {
            throw new IllegalArgumentException("heure de Fin doit être après heureDebut");
        }
        if(fin.isAfter(LocalTime.of(18,00))) {
            throw new IllegalArgumentException("heure de  fin doit être avant 18:00");
        }
        List<Creneau> existants = creneauRepository.findByDateJourOrderByHeureDebutAsc(date);
        boolean chevauchement = existants.stream().anyMatch(c ->
                (debut.isBefore(c.getHeureFin()) && fin.isAfter(c.getHeureDebut()))
        );
        if (chevauchement) {
            throw new IllegalArgumentException("Un créneau existe déjà pour cet intervalle horaire");
        }
        Creneau creneau = new Creneau();
        creneau.setDateJour(date);
        creneau.setHeureDebut(debut);
        creneau.setHeureFin(fin);
        creneau.setTitre(request.getTitre());
        creneau.setReserve(false);
        return creneauRepository.save(creneau);
    }
}
