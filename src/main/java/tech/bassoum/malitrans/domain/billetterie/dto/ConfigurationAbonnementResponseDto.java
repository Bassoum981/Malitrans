package tech.bassoum.malitrans.domain.billetterie.dto;

import java.time.LocalDateTime;

public record ConfigurationAbonnementResponseDto(
        Long id,
        String libelle,
        Double prix,
        Integer dureeJours,
        Boolean actif,
        LocalDateTime dateModification
) {}
