package com.example.projetspringavocat.Services;

import com.example.projetspringavocat.Dtos.AudienceMapper;
import com.example.projetspringavocat.Dtos.AudienceRequest;
import com.example.projetspringavocat.Entities.Affaire;
import com.example.projetspringavocat.Entities.Audience;
import com.example.projetspringavocat.Entities.Creneau;
import com.example.projetspringavocat.Entities.User;
import com.example.projetspringavocat.Repositories.AffaireRepository;
import com.example.projetspringavocat.Repositories.AudienceRepository;
import com.example.projetspringavocat.Repositories.CreneauRepository;
import com.example.projetspringavocat.Repositories.UserRepository;
import com.example.projetspringavocat.exceptions.ResourceNotFoundException;
import com.example.projetspringavocat.security.UserInfoDetails;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@Service
public class AudienceServiceImp {

    private final AudienceRepository audienceRepository;
    private final AffaireRepository affaireRepository;
    private final UserRepository userRepository;
    private final CreneauRepository creneauRepository;
    private final CreneauService creneauService;

    public AudienceServiceImp(AudienceRepository audienceRepository, AffaireRepository affaireRepository,
                              UserRepository userRepository,CreneauRepository creneauRepository,CreneauService creneauService) {
        this.audienceRepository = audienceRepository;
        this.affaireRepository = affaireRepository;
        this.userRepository = userRepository;
        this.creneauRepository=creneauRepository;
        this.creneauService=creneauService;

    }

    public Audience createAudience(AudienceRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("AudienceRequest ne doit pas être null");
        }

        if (request.getAffaireId() == null) {
            throw new IllegalArgumentException("affaireId est obligatoire");
        }

        if (request.getDateAudience() == null) {
            throw new IllegalArgumentException("dateAudience est obligatoire");
        }

        if (request.getDateAudience().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("dateAudience doit être dans le futur");
        }
       DayOfWeek jour = request.getDateAudience().getDayOfWeek();
        if (jour == DayOfWeek.SATURDAY || jour == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("Les audiences ne peuvent pas être planifiées le week-end (Samedi/Dimanche).");
        }


        if (request.getTribunal() == null || request.getTribunal().trim().isEmpty()) {
            throw new IllegalArgumentException("tribunal est obligatoire");
        }
         Optional<Creneau>  c= creneauRepository.findByDateJourBetweenOrderByDateJourAscHeureDebutAsc(request.getDateAudience().toLocalDate(), request.getDateAudience().toLocalDate())
                .stream()
                .filter(creneau -> !creneau.isReserve() && creneau.getHeureDebut().equals(request.getDateAudience().toLocalTime()))
                .findFirst();
        if(!c.isPresent()) {

        }




        Affaire affaire = affaireRepository.findById(request.getAffaireId())
                .orElseThrow(() -> new ResourceNotFoundException("Affaire not found with id: " + request.getAffaireId()));

        Audience audience = AudienceMapper.toEntity(request, affaire);
        return audienceRepository.save(audience);
    }


    public List<Audience> getAudiencesByClient() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserInfoDetails userDetails)) {
            throw new RuntimeException("Utilisateur non authentifié");
        }

        Integer clientId = userDetails.getId();
         User client = userRepository.findById(clientId).orElseThrow(() -> new ResourceNotFoundException("Client not found"));
          List <Audience> ListAUDS =audienceRepository.findByClient(client);
          return ListAUDS;
    }

    public List<Audience> getAudiencesByAffaireId(Long affaireId) {
        if (!affaireRepository.existsById(affaireId)) {
            throw new ResourceNotFoundException("Affaire non trouvée");
        }
        return audienceRepository.findByAffaireId(affaireId);
    }
    @Transactional
    public Audience modifierAudience(Long id, AudienceRequest request) {
        Audience audience = audienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audience not found with id: " + id));

        Affaire affaire = null;
        if (request.getAffaireId() != null) {
            affaire = affaireRepository.findById(request.getAffaireId())
                    .orElseThrow(() -> new ResourceNotFoundException("Affaire not found with id: " + request.getAffaireId()));
        }
        DayOfWeek jour = request.getDateAudience().getDayOfWeek();
        if (jour == DayOfWeek.SATURDAY || jour == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("Les audiences ne peuvent pas être planifiées le week-end (Samedi/Dimanche).");
        }

        AudienceMapper.updateEntityFromRequest(audience, request, affaire);
        return audienceRepository.save(audience);
    }

    public void supprimerAudience(Long id) {
        Audience audience = audienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audience not found with id: " + id));
        audienceRepository.delete(audience);
    }
    @Transactional
    public Audience reporterAudience(Long id, LocalDateTime nouvelleDate, String motif) {
        Audience audience = audienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audience non trouvée"));
        DayOfWeek jour = nouvelleDate.  getDayOfWeek();
        if (jour == DayOfWeek.SATURDAY || jour == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("Les audiences ne peuvent pas être planifiées le week-end (Samedi/Dimanche).");
        }
        String nouvelleDescription = audience.getRemarques() + " [REPORTÉE AU " + nouvelleDate + " : " + motif + "]";

        audience.setDateAudience(nouvelleDate);
        audience.setRemarques(nouvelleDescription);

        return audienceRepository.save(audience);
    }
}
