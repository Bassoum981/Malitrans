package tech.bassoum.malitrans.domain.billetterie.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ConfigurationAbonnementRequestDto(
        @NotBlank(message = "Le libellé est obligatoire")
        String libelle,

        @NotNull(message = "Le prix est obligatoire")
        @Positive(message = "Le prix doit être supérieur à 0")
        Double prix,

        @NotNull(message = "La durée en jours est obligatoire")
        @Positive(message = "La durée doit être supérieure à 0")
        Integer dureeJours,

        Boolean actif
) {}
