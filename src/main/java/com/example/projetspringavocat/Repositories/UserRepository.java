package com.example.projetspringavocat.Repositories;

import com.example.projetspringavocat.Entities.Role;
import com.example.projetspringavocat.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Page<User> findByRoleAndEstVerifie(Role role,Boolean verif ,Pageable pageable);
    long countByRole(Role role);
}
