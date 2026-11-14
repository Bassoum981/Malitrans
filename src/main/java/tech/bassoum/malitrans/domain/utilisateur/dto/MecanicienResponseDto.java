package tech.bassoum.malitrans.domain.utilisateur.dto;

import tech.bassoum.malitrans.domain.utilisateur.enums.RoleUtilisateur;
import tech.bassoum.malitrans.domain.utilisateur.enums.StatutUtilisateur;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data @Builder
public class MecanicienResponseDto {
    private Long id;
    private String nom, prenom, email, telephone, matricule, service, specialite;
    private LocalDate dateEmbauche;
    private Double salaire;
    private StatutUtilisateur statut;
    private RoleUtilisateur role;
}
