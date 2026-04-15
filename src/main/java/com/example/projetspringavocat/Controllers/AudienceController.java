package com.example.projetspringavocat.Controllers;

import com.example.projetspringavocat.Dtos.AudienceMapper;
import com.example.projetspringavocat.Dtos.AudienceRequest;
import com.example.projetspringavocat.Dtos.AudienceResponse;
import com.example.projetspringavocat.Entities.Audience;
import com.example.projetspringavocat.Services.AudienceServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/audiences")
public class AudienceController {

    private final AudienceServiceImp audienceService;

    public AudienceController(AudienceServiceImp audienceService) {
        this.audienceService = audienceService;
    }

    @PostMapping("/secretaire")
    public ResponseEntity<AudienceResponse> createAudience(@RequestBody AudienceRequest request) {
        Audience saved = audienceService.createAudience(request);
        AudienceResponse response = AudienceMapper.toResponse(saved);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/client/mes-audiences")
    public ResponseEntity<List<AudienceResponse>> getAudiencesForCurrentClient() {
        List<Audience> audiences = audienceService.getAudiencesByClient();
        List<AudienceResponse> responses = audiences.stream()
                .map(AudienceMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/secretaire/affaires/{affaireId}")
    public ResponseEntity<List<AudienceResponse>> getAudiencesByAffaire(@PathVariable Long affaireId) {
        List<Audience> audiences = audienceService.getAudiencesByAffaireId(affaireId);
        List<AudienceResponse> responses = audiences.stream()
                .map(AudienceMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/secretaire/{id}")
    public ResponseEntity<AudienceResponse> updateAudience(@PathVariable Long id, @RequestBody AudienceRequest request) {
        Audience updated = audienceService.modifierAudience(id, request);
        AudienceResponse response = AudienceMapper.toResponse(updated);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/secretaire/{id}")
    public ResponseEntity<Void> deleteAudience(@PathVariable Long id) {
        audienceService.supprimerAudience(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/secretaire/postpone/{id}/")
    public ResponseEntity<AudienceResponse> postponeAudience(@PathVariable Long id,
                                                             @RequestParam("nouvelleDate") LocalDateTime nouvelleDate,
                                                             @RequestParam("motif") String motif) {
        Audience reported = audienceService.reporterAudience(id, nouvelleDate, motif);
        AudienceResponse response = AudienceMapper.toResponse(reported);
        return ResponseEntity.ok(response);
    }
}
