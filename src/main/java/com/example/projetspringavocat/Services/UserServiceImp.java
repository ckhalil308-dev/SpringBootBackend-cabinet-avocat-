package com.example.projetspringavocat.Services;

import com.example.projetspringavocat.Dtos.AudienceMapper;
import com.example.projetspringavocat.Dtos.AudienceResponse;
import com.example.projetspringavocat.Dtos.DashboardStatsDTO;
import com.example.projetspringavocat.Entities.Role;
import com.example.projetspringavocat.Entities.StatutAffaire;
import com.example.projetspringavocat.Entities.User;
import com.example.projetspringavocat.Repositories.AffaireRepository;
import com.example.projetspringavocat.Repositories.AudienceRepository;
import com.example.projetspringavocat.Repositories.UserRepository;
import com.example.projetspringavocat.exceptions.ResourceNotFoundException;
import com.example.projetspringavocat.security.UserInfoDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final AffaireRepository affaireRepository;
    private final AudienceRepository audienceRepository;

    public UserServiceImp(UserRepository userRepository, AffaireRepository affaireRepository, AudienceRepository audienceRepository) {
        this.userRepository = userRepository;
        this.affaireRepository = affaireRepository;
        this.audienceRepository = audienceRepository;
    }

    @Override
    public User getClientById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
    @Transactional
    @Override
    public User verifierClient(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        user.setEstVerifie(true);
        return userRepository.save(user);
    }

    @Override
    public Page<User> getAllUsers(int page, int size) {
        if (page < 0) {
            page = 0;
        }
        if (size <= 0) {
            size = 10;
        }

        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findByRoleAndEstVerifie(Role.CLIENT,true, pageable);
    }

    public Page<User> getUsersNonVerif(int page, int size) {
        if (page < 0) {
            page = 0;
        }
        if (size <= 0) {
            size = 10;
        }

        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findByRoleAndEstVerifie(Role.CLIENT,false, pageable);
    }
    @Transactional
    @Override
    public User modifierClient(Integer id, User client) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        user.setNom(client.getNom());
        user.setPrenom(client.getPrenom());
        if (!user.getEmail().equals(client.getEmail())) {
           Optional<User> existantUser = userRepository.findByEmail(client.getEmail());
            if (existantUser.isPresent()) {
                throw new IllegalArgumentException("Cet email est déjà utilisé par un autre compte");
            }
            user.setEmail(client.getEmail());
        }
        user.setNumTelephone(client.getNumTelephone());

        return userRepository.save(user);
    }

    @Override
    public void supprimerClient(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }

    public User getCurrentUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            throw new IllegalArgumentException( "Utilisateur non authentifié");
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof UserInfoDetails userDetails)) {
            throw new IllegalArgumentException(  "Type de principal inattendu");
        }

        Integer userId = userDetails.getId();

        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    @Override
    public DashboardStatsDTO getDashboardStats() {
        Long totalClients = userRepository.countByRole(Role.CLIENT);
        Long affairesEnCours = affaireRepository.countByStatut(StatutAffaire.EN_COURS);
        Long affairesTerminees = affaireRepository.countByStatutIn(List.of(StatutAffaire.GAGNEE, StatutAffaire.PERDUE));

        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        Long audiencesCetteSemaine = audienceRepository.countByDateAudienceBetween(startOfWeek.atStartOfDay(), endOfWeek.atTime(LocalTime.MAX));
        List<AudienceResponse> audiencesSemaine = audienceRepository
                .findByDateAudienceBetween(startOfWeek.atStartOfDay(), endOfWeek.atTime(LocalTime.MAX))
                .stream()
                .map(AudienceMapper::toResponse)
                .toList();

        return new DashboardStatsDTO(totalClients, affairesEnCours, affairesTerminees, audiencesCetteSemaine, audiencesSemaine);
    }


}