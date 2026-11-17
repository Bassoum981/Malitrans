package tech.bassoum.malitrans.domain.billetterie.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AchatBilletRequestDto(
        @NotNull(message = "L'identifiant du passager est obligatoire")
        Long passagerId,

        @NotNull(message = "L'identifiant de l'affectation est obligatoire")
        Long affectationId,

        Long siegeId, // Optionnel (null si placement libre en transport urbain)

        @NotBlank(message = "Le mode de paiement est obligatoire")
        String modePaiement, // Ex: "Orange Money", "Moov", "Espèces"

        String referencePaiement // Référence opérateur (générée auto si vide, ex: paiement espèces)
) {}
