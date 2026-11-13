package tech.bassoum.malitrans.domain.utilisateur.dto;

import tech.bassoum.malitrans.domain.utilisateur.enums.RoleUtilisateur;
import tech.bassoum.malitrans.domain.utilisateur.enums.StatutUtilisateur;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class ChauffeurResponseDto {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String matricule;
    private String service;
    private LocalDate dateEmbauche;
    private String numeroPermis;
    private String categoriePermis;
    private LocalDate dateExpirationPermis;
    private LocalDate dateVisiteMedicale;
    private Double salaire;
    private StatutUtilisateur statut;
    private RoleUtilisateur role;
}
