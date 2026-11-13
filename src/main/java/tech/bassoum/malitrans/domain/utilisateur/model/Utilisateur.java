package tech.bassoum.malitrans.domain.utilisateur.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SuperBuilder;
import tech.bassoum.malitrans.domain.utilisateur.enums.StatutUtilisateur;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "utilisateurs")
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Utilisateur {

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
}
