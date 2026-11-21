package tech.bassoum.malitrans.domain.billetterie.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AchatBilletRequestDto(
        Long passagerId, // Optionnel : null pour un achat ponctuel sans compte passager

        @NotNull(message = "L'affectation est obligatoire")
        Long affectationId,

        Long siegeId,

        @NotBlank(message = "Le mode de paiement est obligatoire")
        String modePaiement,

        String referencePaiement
) {}
