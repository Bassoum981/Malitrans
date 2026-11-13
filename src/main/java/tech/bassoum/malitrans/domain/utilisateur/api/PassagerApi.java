package tech.bassoum.malitrans.domain.utilisateur.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.bassoum.malitrans.domain.utilisateur.dto.PassagerRequestDto;
import tech.bassoum.malitrans.domain.utilisateur.dto.PassagerResponseDto;
import tech.bassoum.malitrans.domain.utilisateur.service.PassagerService;

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
}
