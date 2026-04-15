package com.example.projetspringavocat.Controllers;

import com.example.projetspringavocat.Dtos.AffaireMapper;
import com.example.projetspringavocat.Dtos.AffaireRequest;
import com.example.projetspringavocat.Dtos.AffaireResponse;
import com.example.projetspringavocat.Entities.Affaire;
import com.example.projetspringavocat.Entities.StatutAffaire;
import com.example.projetspringavocat.Services.AffaireService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/affaires")
public class AffaireController {

    private final AffaireService affaireService;

    public AffaireController(AffaireService affaireService) {
        this.affaireService = affaireService;
    }

    @PostMapping("/secretaire/creer")
    public ResponseEntity<AffaireResponse> creeAffaireClient(@RequestBody AffaireRequest affaireRequest) {
        Affaire affaire = affaireService.creeAffaire(affaireRequest);
        return ResponseEntity.ok(AffaireMapper.toResponse(affaire));
    }   

    @GetMapping("/client/mine")
    public ResponseEntity<List<AffaireResponse>> getAffairesForCurrentClient() {
        List<Affaire> affaires = affaireService.getAffairesByClient();
        List<AffaireResponse> response = affaires.stream()
                .map(AffaireMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/secretaire/affaire/{clientId}")
    public ResponseEntity<List<AffaireResponse>> getAffairesByClientId(@PathVariable Integer clientId) {
        List<Affaire> affaires = affaireService.getAffairesByClientId(clientId);
        List<AffaireResponse> response = affaires.stream()
                .map(AffaireMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/secretaire")
    public ResponseEntity<Page<AffaireResponse>> getAllAffaires(
            @RequestParam(required = false) StatutAffaire statut,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Affaire> affairesPage = affaireService.getAll(statut, pageable);
        Page<AffaireResponse> response = affairesPage.map(AffaireMapper::toResponse);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/secretaire/{id}")
    public ResponseEntity<AffaireResponse> getAffaireById(@PathVariable Long id) {
        Affaire affaire = affaireService.getAffaireById(id);
        return ResponseEntity.ok(AffaireMapper.toResponse(affaire));
    }

    @PutMapping("/secretaire/{id}")
    public ResponseEntity<AffaireResponse> modifierAffaire(@PathVariable Long id, @RequestBody AffaireRequest affaireRequest) {
        Affaire updated = affaireService.modifierAffaire(id, affaireRequest);
        return ResponseEntity.ok(AffaireMapper.toResponse(updated));
    }

    @DeleteMapping("/secretaire/{id}")
    public ResponseEntity<Void> supprimerAffaire(@PathVariable Long id) {
        affaireService.supprimerAffaire(id);
        return ResponseEntity.noContent().build();
    }
}
