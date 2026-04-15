package com.example.projetspringavocat.Services;

import com.example.projetspringavocat.Dtos.AffaireRequest;
import com.example.projetspringavocat.Entities.Affaire;
import com.example.projetspringavocat.Entities.StatutAffaire;
import com.example.projetspringavocat.Entities.User;
import com.example.projetspringavocat.Repositories.AffaireRepository;
import com.example.projetspringavocat.Repositories.UserRepository;
import com.example.projetspringavocat.exceptions.ResourceNotFoundException;
import com.example.projetspringavocat.security.UserInfoDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class AffaireServiceImp implements AffaireService {
    AffaireRepository affaireRepository;
    UserRepository userRepository;

    public AffaireServiceImp(AffaireRepository affaireRepository, UserRepository userRepository) {
        this.affaireRepository = affaireRepository;
        this.userRepository = userRepository;
    }

    public Affaire creeAffaire(AffaireRequest affaireRequest) {
         LocalDate today = LocalDate.now();
        if (affaireRequest == null) {
            throw new IllegalArgumentException("AffaireRequest ne doit pas être null");
        }
        if (affaireRequest.getClientId() == 0) {
            throw new IllegalArgumentException("clientId est obligatoire");
        }
        if (affaireRequest.getNumeroDossier() == null || affaireRequest.getNumeroDossier().trim().isEmpty()) {
            throw new IllegalArgumentException("numeroDossier est obligatoire");
        }
        if (affaireRequest.getTitre() == null || affaireRequest.getTitre().trim().isEmpty()) {
            throw new IllegalArgumentException("titre est obligatoire");
        }
        if (affaireRequest.getDescription() == null || affaireRequest.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("description est obligatoire");
        }
        if(affaireRequest.getDateOuverture() == null){
            throw new IllegalArgumentException("dateOuverture est obligatoire");
        }
        if (affaireRequest.getDateOuverture() != null && affaireRequest.getDateOuverture().isBefore(today)) {
            throw new IllegalArgumentException("la date doit etre au futur");

        }
        if (affaireRepository.findAffaireByNumeroDossier(affaireRequest.getNumeroDossier()).isPresent()) {
            throw new IllegalArgumentException("Le numéro de dossier existe déjà");
        }
        int clientId = affaireRequest.getClientId();
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        Affaire affaire = new Affaire();
        affaire.setClient(client);
        affaire.setDescription(affaireRequest.getDescription());
        affaire.setNumeroDossier(affaireRequest.getNumeroDossier());
        affaire.setStatut(affaireRequest.getStatut());
        affaire.setTitre(affaireRequest.getTitre());
        affaire.setDateOuverture(affaireRequest.getDateOuverture());
        return affaireRepository.save(affaire);
    }

    public Page<Affaire> getAll(StatutAffaire statut, Pageable pageable) {
        if (statut == null) {
            return affaireRepository.findAll(pageable);
        }
        return affaireRepository.findByStatut(statut, pageable);
    }

    public Affaire getAffaireById(Long id) {
        return affaireRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Affaire not found with id: " + id));
    }

    public Affaire modifierAffaire(Long id, AffaireRequest affaireRequest) {
        Affaire affaire = affaireRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Affaire not found with id: " + id));

        if (affaireRequest.getNumeroDossier() != null && !affaireRequest.getNumeroDossier().trim().isEmpty()) {
            if (!affaire.getNumeroDossier().equals(affaireRequest.getNumeroDossier()) &&
                    affaireRepository.findAffaireByNumeroDossier(affaireRequest.getNumeroDossier()).isPresent()) {
                throw new IllegalArgumentException("Le numéro de dossier existe déjà");
            }
            affaire.setNumeroDossier(affaireRequest.getNumeroDossier());
        }
        if (affaireRequest.getTitre() != null && !affaireRequest.getTitre().trim().isEmpty()) {
            affaire.setTitre(affaireRequest.getTitre());
        }
        if (affaireRequest.getDescription() != null && !affaireRequest.getDescription().trim().isEmpty()) {
            affaire.setDescription(affaireRequest.getDescription());
        }
        if (affaireRequest.getDateOuverture() != null) {
            affaire.setDateOuverture(affaireRequest.getDateOuverture());
        }
        if(affaireRequest.getDateOuverture() != null && affaireRequest.getDateOuverture().isBefore(LocalDate.now())){
            throw new IllegalArgumentException("la date doit etre au futur");
        }
        if (affaireRequest.getStatut() != null && affaireRequest.getStatut() != affaire.getStatut()) {
            affaire.setStatut(affaireRequest.getStatut());
        }
        return affaireRepository.save(affaire);
    }

    public void supprimerAffaire(Long id) {
        Affaire affaire = affaireRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Affaire not found with id: " + id));
        affaireRepository.delete(affaire);
    }

    public List<Affaire> getAffairesByClient() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserInfoDetails userDetails)) {
            throw new RuntimeException("Utilisateur non authentifié");
        }
        Integer userId = userDetails.getId();
        List<Affaire> affaires = affaireRepository.findAffaireByClientId(userId);
        return affaires;
    }

    public List<Affaire> getAffairesByClientId(Integer clientId) {
        User c = userRepository.findById(clientId).orElseThrow(() ->
                new ResourceNotFoundException("Client not found with id: " + clientId)
        );
        List<Affaire> affaires = affaireRepository.findAffaireByClientId(clientId);
        return affaires;
    }
}
