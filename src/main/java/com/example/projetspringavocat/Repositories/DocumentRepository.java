package com.example.projetspringavocat.Repositories;

import com.example.projetspringavocat.Entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}

