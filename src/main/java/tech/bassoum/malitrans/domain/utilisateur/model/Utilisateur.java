package tech.bassoum.malitrans.domain.utilisateur.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tech.bassoum.malitrans.domain.utilisateur.enums.StatutUtilisateur;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "utilisateurs")
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Utilisateur implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private String motDePasse;

    private String photo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutUtilisateur statut;

    // --- Implémentation de UserDetails pour Spring Security ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Attribue un rôle dynamique basé sur le type d'entité (ex: ROLE_PASSAGER, ROLE_EMPLOYE, ROLE_CHAUFFEUR)
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.getClass().getSimpleName().toUpperCase()));
    }

    @Override
    public String getPassword() {
        return this.motDePasse;
    }

    @Override
    public String getUsername() {
        return this.email; // On utilise l'email comme identifiant unique de connexion
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Le compte n'est pas bloqué si le statut n'est pas SUSPENDU
        return this.statut != StatutUtilisateur.SUSPENDU;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Le compte est actif si son statut est ACTIF
        return this.statut == StatutUtilisateur.ACTIF;
    }
}
