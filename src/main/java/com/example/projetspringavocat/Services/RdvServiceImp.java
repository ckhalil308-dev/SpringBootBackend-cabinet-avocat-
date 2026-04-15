package com.example.projetspringavocat.Services;

import com.example.projetspringavocat.Dtos.RdvMapper;
import com.example.projetspringavocat.Dtos.RdvRequestDTO;
import com.example.projetspringavocat.Dtos.RdvResponseDTO;
import com.example.projetspringavocat.Entities.Creneau;
import com.example.projetspringavocat.Entities.RendezVous;
import com.example.projetspringavocat.Entities.StatusRdv;
import com.example.projetspringavocat.Entities.Role;
import com.example.projetspringavocat.Entities.User;
import com.example.projetspringavocat.Repositories.CreneauRepository;
import com.example.projetspringavocat.Repositories.RendezVousRepository;
import com.example.projetspringavocat.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RdvServiceImp implements RdvService {
    private final RendezVousRepository rendezVousRepository;
    private final CreneauRepository creneauRepository;
    private final UserService userService;

    public RdvServiceImp(RendezVousRepository rendezVousRepository, CreneauRepository creneauRepository, UserService userService) {
        this.rendezVousRepository = rendezVousRepository;
        this.creneauRepository = creneauRepository;
        this.userService = userService;
    }

    @Override
    public RdvResponseDTO proposerRdv(RdvRequestDTO request) {
        if (request == null || request.getCreneauId() == null || !StringUtils.hasText(request.getMotif())) {
            throw new IllegalArgumentException("Motif et creneauId sont requis");
        }
        Creneau creneau = creneauRepository.findByIdAndReserveFalse(request.getCreneauId())
                .orElseThrow(() -> new ResourceNotFoundException("Creneau non disponible"));

        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        boolean isPastDate = creneau.getDateJour().isBefore(today);
        boolean isPastSameDay = creneau.getDateJour().isEqual(today) && creneau.getHeureDebut().isBefore(now);
        if (isPastDate || isPastSameDay) {
            throw new IllegalArgumentException("Impossible de réserver un rdv dans le passé");
        }

        User client = userService.getCurrentUserEntity();
        boolean hasActiveRdv = rendezVousRepository.existsByClientIdAndStatus(
                client.getId(),
                StatusRdv.PENDING
        );

        if (hasActiveRdv) {
            throw new IllegalStateException("Vous avez déjà  en cours d'examen.");
        }

        RendezVous rendezVous = new RendezVous();
        rendezVous.setMotif(request.getMotif());
        rendezVous.setStatus(StatusRdv.PENDING);
        rendezVous.setClient(client);
        rendezVous.setCreneau(creneau);

        RendezVous saved = rendezVousRepository.save(rendezVous);
        return RdvMapper.toResponse(saved);
    }

    @Override
    public RdvResponseDTO confirmerRdv(Integer rdvId) {
        RendezVous rendezVous = rendezVousRepository.findById(rdvId)
                .orElseThrow(() -> new ResourceNotFoundException("RDV introuvable"));

        if (rendezVous.getStatus() == StatusRdv.CONFIRMED) {
            throw new IllegalStateException("RDV déjà confirmé");
        }
        if (rendezVous.getCreneau().isReserve()) {
            throw new IllegalStateException("Creneau déjà réservé");
        }

        rendezVous.setStatus(StatusRdv.CONFIRMED);
        Creneau creneau = rendezVous.getCreneau();
        creneau.setReserve(true);
        creneau.setTitre("RDV avec " + rendezVous.getClient().getPrenom() + " " + rendezVous.getClient().getNom());
        creneauRepository.save(creneau);

        RendezVous saved = rendezVousRepository.save(rendezVous);
        return RdvMapper.toResponse(saved);
    }

    @Override
    public RdvResponseDTO annulerRdv(Integer rdvId, String motifRefus) {
        if (!StringUtils.hasText(motifRefus)) {
            throw new IllegalArgumentException("Le motif de refus est obligatoire");
        }

        RendezVous rendezVous = rendezVousRepository.findById(rdvId)
                .orElseThrow(() -> new ResourceNotFoundException("RDV introuvable"));

        rendezVous.setStatus(StatusRdv.CANCELLED);
        rendezVous.setMessage(motifRefus);
        Creneau creneau = rendezVous.getCreneau();
        creneau.setReserve(false);
        creneauRepository.save(creneau);

        RendezVous saved = rendezVousRepository.save(rendezVous);
        return RdvMapper.toResponse(saved);
    }

    @Override
    public List<RdvResponseDTO> getRdvsPourSecretaire(StatusRdv status) {
        List<RendezVous> rdvs = status != null
                ? rendezVousRepository.findByStatus(status)
                : rendezVousRepository.findAll();
        return rdvs.stream().map(RdvMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<RdvResponseDTO> getRdvsPourClient(StatusRdv status) {
        User client = userService.getCurrentUserEntity();
        List<RendezVous> rdvs = status != null
                ? rendezVousRepository.findByClientIdAndStatus(client.getId(), status)
                : rendezVousRepository.findByClientId(client.getId());
        return rdvs.stream().map(RdvMapper::toResponse).collect(Collectors.toList());
    }
}
