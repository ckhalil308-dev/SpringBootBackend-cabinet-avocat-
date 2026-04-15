package com.example.projetspringavocat.Services;


import com.example.projetspringavocat.Entities.User;
import com.example.projetspringavocat.Repositories.UserRepository;
import com.example.projetspringavocat.exceptions.ResourceNotFoundException;
import com.example.projetspringavocat.security.UserInfoDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(UserInfoDetails::build)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

}
