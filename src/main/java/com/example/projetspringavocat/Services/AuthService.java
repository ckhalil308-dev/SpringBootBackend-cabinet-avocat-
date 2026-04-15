package com.example.projetspringavocat.Services;
import com.example.projetspringavocat.Dtos.AuthResponse;
import com.example.projetspringavocat.Dtos.LoginRequest;
import com.example.projetspringavocat.Entities.User;
import com.example.projetspringavocat.Entities.Role;
import com.example.projetspringavocat.Repositories.UserRepository;
import com.example.projetspringavocat.security.UserInfoDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class AuthService {
     @Autowired
     private UserRepository userRepository;
     @Autowired
     private AuthenticationManager authenticationManager;
     @Autowired
     private PasswordEncoder passwordEncoder;
     @Autowired
     private  JwtService jwtService;
     public User register(User client) {
          if(userRepository.findByEmail(client.getEmail()).isPresent()){
              throw new IllegalArgumentException("Cet email est déjà utilisé par un autre compte");
          }
          User newUser = new User();
          newUser.setNom(client.getNom());
          newUser.setPrenom(client.getPrenom());
          newUser.setEmail(client.getEmail());
          newUser.setPassword(passwordEncoder.encode(client.getPassword()));
          newUser.setNumTelephone(client.getNumTelephone());
          newUser.setRole(Role.CLIENT);

          return userRepository.save(newUser);
     }
    public AuthResponse login( LoginRequest request) {
         Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
         );

        UserInfoDetails userDetails = (UserInfoDetails) authentication.getPrincipal();
        if(!userDetails.isEstVerifie()){
            throw new IllegalStateException("Votre compte n'est pas encore vérifié par la secretaire. Veuillez patienter.");
        }
        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token, userDetails.getFullName(),userDetails.getAuthorities().toString() );
    }

}
