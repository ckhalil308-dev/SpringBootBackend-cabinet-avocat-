package com.example.projetspringavocat.Repositories;

import com.example.projetspringavocat.Entities.RendezVous;
import com.example.projetspringavocat.Entities.StatusRdv;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RendezVousRepository extends JpaRepository<RendezVous, Integer> {
	Optional<RendezVous> findById(Integer id);
	boolean existsByCreneauIdAndStatus(Integer creneauId, StatusRdv status);
	boolean existsByClientIdAndStatus(Integer clientId, StatusRdv status);
	boolean existsByClientIdAndStatusIn(Integer clientId, List<StatusRdv> statuses);
	List<RendezVous> findByStatus(StatusRdv status);
	List<RendezVous> findByClientId(Integer clientId);
	List<RendezVous> findByClientIdAndStatus(Integer clientId, StatusRdv status);
}

