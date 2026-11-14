package tech.bassoum.malitrans.domain.utilisateur.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder; // <--- INDISPENSABLE : Ne pas oublier cet import !
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tech.bassoum.malitrans.domain.utilisateur.enums.RoleUtilisateur;
import tech.bassoum.malitrans.domain.utilisateur.enums.StatutUtilisateur;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "utilisateurs")
@SuperBuilder // <--- Active le builder en cascade
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length =50)
    private RoleUtilisateur role;

    // --- Implémentation de UserDetails pour Spring Security ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getPassword() {
        return this.motDePasse;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.statut != StatutUtilisateur.SUSPENDU;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.statut == StatutUtilisateur.ACTIF;
    }
}
