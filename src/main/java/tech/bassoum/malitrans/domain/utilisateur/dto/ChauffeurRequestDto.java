package tech.bassoum.malitrans.domain.utilisateur.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ChauffeurRequestDto {

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

    // --- Informations Spécifiques Chauffeur ---
    @NotBlank(message = "Le numéro de permis est obligatoire")
    private String numeroPermis;

    @NotBlank(message = "La catégorie du permis est obligatoire")
    private String categoriePermis; // Ex: A, B, C, D, etc.

    @NotNull(message = "La date d'expiration du permis est obligatoire")
    private LocalDate dateExpirationPermis;

    private LocalDate dateVisiteMedicale;

    @NotNull(message = "Le salaire est obligatoire")
    @Positive(message = "Le salaire doit être supérieur à zéro")
    private Double salaire;
}
