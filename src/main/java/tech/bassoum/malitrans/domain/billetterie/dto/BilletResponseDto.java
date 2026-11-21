package tech.bassoum.malitrans.domain.billetterie.dto;

import tech.bassoum.malitrans.domain.billetterie.enums.StatutBillet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record BilletResponseDto(
        Long id,
        String numeroBillet,
        LocalDateTime dateEmission,
        StatutBillet statut,
        Double montantPaye,
        String modePaiement,
        String referencePaiement,
        Long passagerId,
        String passagerNomComplet,
        Long affectationId,
        String ligneNumero,
        String ligneNom,
        LocalDate dateTrajet,
        LocalTime heureDebut,
        String busImmatriculation,
        String siegeNumero
) {}
