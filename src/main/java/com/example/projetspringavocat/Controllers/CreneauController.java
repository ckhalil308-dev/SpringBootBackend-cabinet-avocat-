package com.example.projetspringavocat.Controllers;

import com.example.projetspringavocat.Dtos.*;
import com.example.projetspringavocat.Entities.Creneau;
import com.example.projetspringavocat.Services.CreneauService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/creneaux")
public class CreneauController {

    private final CreneauService creneauService;

    public CreneauController(CreneauService creneauService) {
        this.creneauService = creneauService;
    }

    @GetMapping("/client/disponibles")
    public ResponseEntity<List<CreneauResClient>> getDisponiblesPourClient(
            @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<Creneau> creneaux = creneauService.getDisponiblesParDate(date);
        List<CreneauResClient> body = creneaux.stream()
                .map(CreneauMapper::toClientDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(body);
    }

    @PostMapping("/secretaire/journal")
    public ResponseEntity<ApiResponse> genererCreneauxJournaliers(
            @RequestParam(value = "date" , required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ){
        creneauService.genererCreneauxJournaliers(date);

        return ResponseEntity.ok( new ApiResponse(true,"creneaux creer ",""));

    }

    @GetMapping("/client/disponible/{date}")
    public ResponseEntity<List<CreneauResClient>> getDisponiblesParDate(
            @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate date
    )
    {

        List<Creneau> creneaux = creneauService.getDisponiblesParDate(date);
        List<CreneauResClient> body = creneaux.stream()
                .map(CreneauMapper::toClientDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(body);
    }

    @GetMapping("/secretaire/{id}")
    public ResponseEntity<CreneauResSecretaire> getCreneauPourSecretaire(@PathVariable("id") Integer id) {
        Creneau creneau = creneauService.getById(id);
        CreneauResSecretaire dto = CreneauMapper.toSecretaireDto(creneau);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<CreneauResClient> getCreneauPourClient(@PathVariable("id") Integer id) {
        Creneau creneau = creneauService.getById(id);
        CreneauResClient dto = CreneauMapper.toClientDto(creneau);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/secretaire/{id}/bloquer")
    public ResponseEntity<CreneauResSecretaire> bloquerCreneau(
            @PathVariable("id") Integer id,
            @RequestParam(value = "motif",required = false) String motif
    ) {
        Creneau c=creneauService.bloquerCreneau(id, motif);
        return ResponseEntity.ok(CreneauMapper.toSecretaireDto(c));
    }

    @GetMapping("/secretaire/planning-semaine")
    public ResponseEntity<List<CreneauResSecretaire>> getPlanningSemaineSecretaire(
            @RequestParam("dateReference")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateReference
    ) {
        List<Creneau> creneaux = creneauService.getPlanningSemaine(dateReference);
        List<CreneauResSecretaire> body = creneaux.stream()
                .map(CreneauMapper::toSecretaireDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(body);
    }

    @GetMapping("/client/planning-semaine")
    public ResponseEntity<List<CreneauResClient>> getPlanningSemaineClient(
            @RequestParam("dateReference") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateReference
    ) {
        List<Creneau> creneaux = creneauService.getPlanningSemaine(dateReference);
        List<CreneauResClient> body = creneaux.stream()
                .map(CreneauMapper::toClientDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/secretaire/{id}")
    public ResponseEntity<ApiResponse> supprimerCreneau(@PathVariable("id") Integer id) {
        creneauService.supprimerCreneau(id);
        return ResponseEntity.ok(new ApiResponse(true, "Créneau supprimé", ""));
    }

    @PutMapping("/secretaire/{id}")
    public ResponseEntity<CreneauResSecretaire> modifierCreneau(
            @PathVariable("id") Integer id,
            @RequestBody ModifierCreneauDto mcd
            ) {
        Creneau creneau = creneauService.modifierCreneau(id, mcd);
        CreneauResSecretaire dto = CreneauMapper.toSecretaireDto(creneau);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/secretaire")
    public ResponseEntity<CreneauResSecretaire> creerCreneau(@RequestBody CreneauRequest request) {
        Creneau creneau = creneauService.creerCreneau(request);
        CreneauResSecretaire dto = CreneauMapper.toSecretaireDto(creneau);
        return ResponseEntity.ok(dto);
    }
}
