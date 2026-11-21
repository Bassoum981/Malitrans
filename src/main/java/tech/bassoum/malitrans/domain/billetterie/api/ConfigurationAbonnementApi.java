package tech.bassoum.malitrans.domain.billetterie.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.bassoum.malitrans.domain.billetterie.dto.ConfigurationAbonnementRequestDto;
import tech.bassoum.malitrans.domain.billetterie.dto.ConfigurationAbonnementResponseDto;
import tech.bassoum.malitrans.domain.billetterie.service.ConfigurationAbonnementService;

import java.util.List;

@RestController
@RequestMapping("/api/configurations-abonnement")
@RequiredArgsConstructor
public class ConfigurationAbonnementApi {

    private final ConfigurationAbonnementService configurationService;

    @GetMapping("/active")
    public ResponseEntity<ConfigurationAbonnementResponseDto> getActive() {
        return ResponseEntity.ok(configurationService.getActiveDto());
    }

    @GetMapping
    public ResponseEntity<List<ConfigurationAbonnementResponseDto>> getAll() {
        return ResponseEntity.ok(configurationService.getAll());
    }

    @PostMapping
    public ResponseEntity<ConfigurationAbonnementResponseDto> create(@Valid @RequestBody ConfigurationAbonnementRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(configurationService.createConfiguration(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConfigurationAbonnementResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody ConfigurationAbonnementRequestDto request
    ) {
        return ResponseEntity.ok(configurationService.updateConfiguration(id, request));
    }
}
