package com.example.projetspringavocat.Controllers;

import com.example.projetspringavocat.Dtos.RdvRequestDTO;
import com.example.projetspringavocat.Dtos.RdvResponseDTO;
import com.example.projetspringavocat.Entities.StatusRdv;
import com.example.projetspringavocat.Services.RdvService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rdv")
public class RdvController {
    private final RdvService rdvService;

    public RdvController(RdvService rdvService) {
        this.rdvService = rdvService;
    }

    @PostMapping("/client/proposer")
    public ResponseEntity<RdvResponseDTO> proposer(@RequestBody RdvRequestDTO request) {
        return ResponseEntity.ok(rdvService.proposerRdv(request));
    }

    @PutMapping("/secretaire/{id}/confirmer")
    public ResponseEntity<RdvResponseDTO> confirmer(@PathVariable Integer id) {
        return ResponseEntity.ok(rdvService.confirmerRdv(id));
    }

    @PutMapping("/secretaire/{id}/annuler")
    public ResponseEntity<RdvResponseDTO> annuler(@PathVariable Integer id, @RequestParam String motifRefus) {
        return ResponseEntity.ok(rdvService.annulerRdv(id, motifRefus));
    }

    @GetMapping("/secretaire")
    public ResponseEntity<java.util.List<RdvResponseDTO>> listerPourSecretaire(@RequestParam(required = false) StatusRdv status) {
        return ResponseEntity.ok(rdvService.getRdvsPourSecretaire(status));
    }

    @GetMapping("/client")
    public ResponseEntity<java.util.List<RdvResponseDTO>> listerPourClient(@RequestParam(required = false) StatusRdv status) {
        return ResponseEntity.ok(rdvService.getRdvsPourClient(status));
    }
}

