package tech.bassoum.malitrans.domain.utilisateur.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.bassoum.malitrans.domain.utilisateur.enums.RoleUtilisateur;
import tech.bassoum.malitrans.domain.utilisateur.enums.StatutUtilisateur;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponseDto {
    private String token;
    private String type = "Bearer";
    private String email;
    private String nomComplet;
    private RoleUtilisateur role;       // Ajouté ici
    private StatutUtilisateur statut;
}
