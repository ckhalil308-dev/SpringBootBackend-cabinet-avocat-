package com.example.projetspringavocat.security;
import com.example.projetspringavocat.Entities.Role;
import com.example.projetspringavocat.Entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class UserInfoDetails implements UserDetails {

    private final Integer id;
    private final String nom;
    private final String prenom;
    private final String email;
    private final String password;
    private final boolean estVerifie;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserInfoDetails(Integer id,
                           String email,
                           String password,
                           boolean estVerifie,
                           Collection<? extends GrantedAuthority> authorities,
                           String nom, String prenom      ) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.estVerifie = estVerifie;
        this.authorities = authorities;
        this.nom = nom;
        this.prenom = prenom;

    }

    public static UserInfoDetails build(User user) {
        Role role = user.getRole();
        String authorityName = "ROLE_" + role.name();
        List<GrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority(authorityName));

        return new UserInfoDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.isEstVerifie(),
                authorities,
                user.getNom(),
                user.getPrenom()
        );
    }

    public Integer getId() {
        return id;
    }
    public  String getFullName() {
        return nom + " " + prenom;
    }
    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
//        return estVerifie;
        return true;
    }
    public boolean isEstVerifie() {
        return estVerifie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfoDetails that = (UserInfoDetails) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);}
}

