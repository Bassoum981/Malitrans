package tech.bassoum.malitrans.domain.billetterie.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.bassoum.malitrans.domain.billetterie.dto.AchatBilletRequestDto;
import tech.bassoum.malitrans.domain.billetterie.dto.BilletResponseDto;
import tech.bassoum.malitrans.domain.billetterie.service.BilletService;

import java.util.List;

@RestController
@RequestMapping("/api/billets")
@RequiredArgsConstructor
public class BilletApi {

    private final BilletService billetService;

    @PostMapping("/acheter")
    public ResponseEntity<BilletResponseDto> acheterBillet(@Valid @RequestBody AchatBilletRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(billetService.acheterBillet(request));
    }

    @GetMapping("/passager/{passagerId}")
    public ResponseEntity<List<BilletResponseDto>> getBilletsByPassager(@PathVariable Long passagerId) {
        return ResponseEntity.ok(billetService.getBilletsByPassager(passagerId));
    }

    // Endpoint appelé lors du scan du QR Code par le contrôleur à bord
    @PostMapping("/controler/{numeroBillet}")
    public ResponseEntity<BilletResponseDto> controlerBillet(@PathVariable String numeroBillet) {
        return ResponseEntity.ok(billetService.validerEmbarquement(numeroBillet));
    }
}
