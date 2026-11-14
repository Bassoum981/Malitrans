package tech.bassoum.malitrans.domain.utilisateur.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CaissierRequestDto {

    // --- Informations Utilisateur de base ---
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;

    @NotBlank(message = "Le téléphone est obligatoire")
    private String telephone;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String motDePasse;

    // --- Informations Employé ---
    @NotBlank(message = "Le matricule est obligatoire")
    private String matricule;

    private LocalDate dateEmbauche;

    private String service;

    @NotNull(message = "Le salaire est obligatoire")
    @Positive(message = "Le salaire doit être supérieur à zéro")
    private Double salaire;

    // --- Informations Spécifiques Caissier ---
    @NotBlank(message = "L'identifiant de la caisse est obligatoire")
    private String caisseId;

    @NotBlank(message = "La zone est obligatoire")
    private String zone;
}
