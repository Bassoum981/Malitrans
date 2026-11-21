package tech.bassoum.malitrans.domain.billetterie.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bassoum.malitrans.domain.billetterie.dto.ConfigurationAbonnementRequestDto;
import tech.bassoum.malitrans.domain.billetterie.dto.ConfigurationAbonnementResponseDto;
import tech.bassoum.malitrans.domain.billetterie.model.ConfigurationAbonnement;
import tech.bassoum.malitrans.domain.billetterie.repository.ConfigurationAbonnementRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConfigurationAbonnementService {

    private final ConfigurationAbonnementRepository configRepository;

    @Transactional(readOnly = true)
    public ConfigurationAbonnement getConfigurationActive() {
        return configRepository.findFirstByActifTrueOrderByIdDesc()
                .orElseThrow(() -> new RuntimeException("Aucune configuration d'abonnement active trouvée."));
    }

    @Transactional(readOnly = true)
    public ConfigurationAbonnementResponseDto getActiveDto() {
        return mapToResponse(getConfigurationActive());
    }

    @Transactional(readOnly = true)
    public List<ConfigurationAbonnementResponseDto> getAll() {
        return configRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public ConfigurationAbonnementResponseDto updateConfiguration(Long id, ConfigurationAbonnementRequestDto request) {
        ConfigurationAbonnement config = configRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Configuration introuvable avec l'ID : " + id));

        config.setLibelle(request.libelle());
        config.setPrix(request.prix());
        config.setDureeJours(request.dureeJours());
        if (request.actif() != null) {
            config.setActif(request.actif());
        }

        return mapToResponse(configRepository.save(config));
    }

    @Transactional
    public ConfigurationAbonnementResponseDto createConfiguration(ConfigurationAbonnementRequestDto request) {
        if (Boolean.TRUE.equals(request.actif())) {
            configRepository.findAll().forEach(c -> {
                c.setActif(false);
                configRepository.save(c);
            });
        }

        ConfigurationAbonnement config = ConfigurationAbonnement.builder()
                .libelle(request.libelle())
                .prix(request.prix())
                .dureeJours(request.dureeJours())
                .actif(request.actif() != null ? request.actif() : true)
                .build();

        return mapToResponse(configRepository.save(config));
    }

    private ConfigurationAbonnementResponseDto mapToResponse(ConfigurationAbonnement c) {
        return new ConfigurationAbonnementResponseDto(
                c.getId(),
                c.getLibelle(),
                c.getPrix(),
                c.getDureeJours(),
                c.getActif(),
                c.getDateModification()
        );
    }
}
