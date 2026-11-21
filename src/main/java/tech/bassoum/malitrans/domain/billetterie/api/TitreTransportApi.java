package tech.bassoum.malitrans.domain.billetterie.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.bassoum.malitrans.domain.billetterie.dto.SouscriptionTitreRequestDto;
import tech.bassoum.malitrans.domain.billetterie.dto.TitreTransportResponseDto;
import tech.bassoum.malitrans.domain.billetterie.service.TitreTransportService;

import java.util.List;

@RestController
@RequestMapping("/api/titres-transport")
@RequiredArgsConstructor
public class TitreTransportApi {

    private final TitreTransportService titreTransportService;

    @PostMapping("/souscrire")
    public ResponseEntity<TitreTransportResponseDto> souscrire(@Valid @RequestBody SouscriptionTitreRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(titreTransportService.souscrireOuRenouveler(request));
    }

    @GetMapping("/passager/{passagerId}")
    public ResponseEntity<List<TitreTransportResponseDto>> getByPassager(@PathVariable Long passagerId) {
        return ResponseEntity.ok(titreTransportService.getTitresByPassager(passagerId));
    }

    @GetMapping
    public ResponseEntity<List<TitreTransportResponseDto>> getAll() {
        return ResponseEntity.ok(titreTransportService.getAllTitres());
    }

    // Contrôle du QR code d'abonnement à bord (vérifie la validité sans invalider le pass)
    @PostMapping("/controler/{codeTitre}")
    public ResponseEntity<TitreTransportResponseDto> controlerTitre(@PathVariable String codeTitre) {
        return ResponseEntity.ok(titreTransportService.verifierTitre(codeTitre));
    }
}
