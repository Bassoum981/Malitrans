package tech.bassoum.malitrans.domain.billetterie.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SouscriptionTitreRequestDto(
        @NotNull(message = "L'identifiant du passager est obligatoire")
        Long passagerId,

        @NotBlank(message = "Le mode de paiement est obligatoire")
        String modePaiement,

        String referencePaiement
) {}
