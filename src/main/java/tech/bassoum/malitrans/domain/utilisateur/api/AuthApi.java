package tech.bassoum.malitrans.domain.utilisateur.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.bassoum.malitrans.domain.utilisateur.dto.JwtResponseDto;
import tech.bassoum.malitrans.domain.utilisateur.dto.LoginRequestDto;
import tech.bassoum.malitrans.domain.utilisateur.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApi {

    private final AuthService authService;

    @PostMapping("/connexion")
    public ResponseEntity<JwtResponseDto> connecter(@Valid @RequestBody LoginRequestDto requestDto) {
        JwtResponseDto response = authService.connecter(requestDto);
        return ResponseEntity.ok(response);
    }
}
