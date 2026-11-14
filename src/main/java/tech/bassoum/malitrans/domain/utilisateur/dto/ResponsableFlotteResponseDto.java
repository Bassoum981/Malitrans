package tech.bassoum.malitrans.domain.utilisateur.dto;

import lombok.Builder;
import lombok.Data;
import tech.bassoum.malitrans.domain.utilisateur.enums.RoleUtilisateur;
import tech.bassoum.malitrans.domain.utilisateur.enums.StatutUtilisateur;
import java.time.LocalDate;

@Data
@Builder
public class ResponsableFlotteResponseDto {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String matricule;
    private LocalDate dateEmbauche;
    private String service;
    private Double salaire;
    private String grade;
    private StatutUtilisateur statut;
    private RoleUtilisateur role;
}
