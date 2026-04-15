package com.example.projetspringavocat.Services;

import com.example.projetspringavocat.Dtos.DashboardStatsDTO;
import com.example.projetspringavocat.Entities.User;
import org.springframework.data.domain.Page;

public interface UserService {
    Page<User> getUsersNonVerif(int page, int size);
    User getClientById(Integer id);
    User verifierClient(Integer id);
    Page<User> getAllUsers(int page, int size);
    User modifierClient(Integer id, User client);
    void supprimerClient(Integer id);
    User getCurrentUserEntity() ;
    DashboardStatsDTO getDashboardStats();
}
