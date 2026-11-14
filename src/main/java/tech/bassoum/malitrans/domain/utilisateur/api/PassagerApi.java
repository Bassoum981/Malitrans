package tech.bassoum.malitrans.domain.utilisateur.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.bassoum.malitrans.domain.utilisateur.dto.PassagerRequestDto;
import tech.bassoum.malitrans.domain.utilisateur.dto.PassagerResponseDto;
import tech.bassoum.malitrans.domain.utilisateur.service.PassagerService;

import java.util.List;

@RestController
@RequestMapping("/api/auth/passagers")
@RequiredArgsConstructor
public class PassagerApi {

    private final PassagerService passagerService;

    @PostMapping("/inscription")
    public ResponseEntity<PassagerResponseDto> inscrire(@Valid @RequestBody PassagerRequestDto requestDto) {
        PassagerResponseDto nouveauPassager = passagerService.inscrirePassager(requestDto);
        return new ResponseEntity<>(nouveauPassager, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PassagerResponseDto>> listerPassagers() {
        return ResponseEntity.ok(passagerService.listerPassagers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassagerResponseDto> obtenirPassagerParId(@PathVariable Long id) {
        return ResponseEntity.ok(passagerService.recupererPassagerParId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PassagerResponseDto> modifierPassager(@PathVariable Long id, @Valid @RequestBody PassagerRequestDto requestDto) {
        return ResponseEntity.ok(passagerService.modifierPassager(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerPassager(@PathVariable Long id) {
        passagerService.supprimerPassager(id);
        return ResponseEntity.noContent().build();
    }
}
