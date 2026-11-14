package tech.bassoum.malitrans.domain.utilisateur.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class MecanicienRequestDto {
    @NotBlank(message = "Le nom est obligatoire") private String nom;
    @NotBlank(message = "Le prénom est obligatoire") private String prenom;
    @NotBlank(message = "L'email est obligatoire") @Email(message = "Format d'email invalide") private String email;
    @NotBlank(message = "Le téléphone est obligatoire") private String telephone;
    @NotBlank(message = "Le mot de passe est obligatoire") @Size(min = 6, message = "Au moins 6 caractères") private String motDePasse;

    @NotBlank(message = "Le matricule est obligatoire") private String matricule;
    private LocalDate dateEmbauche;
    private String service;
    @NotNull(message = "Le salaire est obligatoire") @Positive private Double salaire;

    @NotBlank(message = "La spécialité est obligatoire") private String specialite; // Ex: Moteur, Électronique, Pneumatique
}
