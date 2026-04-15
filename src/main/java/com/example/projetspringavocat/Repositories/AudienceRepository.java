package com.example.projetspringavocat.Repositories;

import com.example.projetspringavocat.Entities.Audience;
import com.example.projetspringavocat.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AudienceRepository extends JpaRepository<Audience, Long> {
    @Query("SELECT a FROM Audience a JOIN a.affaire aff WHERE aff.client = :client")
    List<Audience> findByClient(@Param("client") User client);
    List <Audience> findByAffaireId(Long affaireId);
    long countByDateAudienceBetween(LocalDateTime start, LocalDateTime end);
    List<Audience> findByDateAudienceBetween(LocalDateTime start, LocalDateTime end);

}

