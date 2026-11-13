package tech.bassoum.malitrans.domain.utilisateur.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.bassoum.malitrans.domain.utilisateur.dto.ChauffeurRequestDto;
import tech.bassoum.malitrans.domain.utilisateur.dto.ChauffeurResponseDto;
import tech.bassoum.malitrans.domain.utilisateur.service.ChauffeurService;

@RestController
@RequestMapping("/api/admin/employes")
@RequiredArgsConstructor
public class EmployeAdminApi {

    private final ChauffeurService chauffeurService;

    @PostMapping("/chauffeurs")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // Sécurisé : Seul un ADMIN peut créer un chauffeur
    public ResponseEntity<ChauffeurResponseDto> creerChauffeur(@Valid @RequestBody ChauffeurRequestDto requestDto) {
        ChauffeurResponseDto response = chauffeurService.creerChauffeur(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
