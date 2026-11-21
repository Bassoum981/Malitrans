package tech.bassoum.malitrans.domain.billetterie.dto;

import tech.bassoum.malitrans.domain.billetterie.enums.StatutTitreTransport;

import java.time.LocalDateTime;

public record TitreTransportResponseDto(
        Long id,
        String codeTitre,
        LocalDateTime dateDebut,
        LocalDateTime dateFin,
        Double montantPaye,
        StatutTitreTransport statut,
        boolean valideActuellement,
        Long passagerId,
        String passagerNomComplet,
        String modePaiement,
        String referencePaiement
) {}
