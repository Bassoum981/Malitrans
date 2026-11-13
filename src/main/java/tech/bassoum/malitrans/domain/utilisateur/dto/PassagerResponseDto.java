package tech.bassoum.malitrans.domain.utilisateur.dto;

import tech.bassoum.malitrans.domain.utilisateur.enums.StatutUtilisateur;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PassagerResponseDto {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String adresse;
    private String cni;
    private StatutUtilisateur statut;
}
