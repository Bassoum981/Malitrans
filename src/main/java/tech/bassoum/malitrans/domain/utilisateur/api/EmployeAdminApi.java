package tech.bassoum.malitrans.domain.utilisateur.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.bassoum.malitrans.domain.utilisateur.dto.*;
import tech.bassoum.malitrans.domain.utilisateur.service.CaissierService;
import tech.bassoum.malitrans.domain.utilisateur.service.ChauffeurService;
import tech.bassoum.malitrans.domain.utilisateur.service.MecanicienService;

@RestController
@RequestMapping("/api/admin/employes")
@RequiredArgsConstructor
public class EmployeAdminApi {

    private final ChauffeurService chauffeurService;
    private final CaissierService caissierService;

    @PostMapping("/chauffeurs")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // Sécurisé : Seul un ADMIN peut créer un chauffeur
    public ResponseEntity<ChauffeurResponseDto> creerChauffeur(@Valid @RequestBody ChauffeurRequestDto requestDto) {
        ChauffeurResponseDto response = chauffeurService.creerChauffeur(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/caissiers")
    @PreAuthorize("hasRole('ADMIN')") // Sécurisé : Seul un ADMIN peut créer un caissier
    public ResponseEntity<CaissierResponseDto> creerCaissier(@Valid @RequestBody CaissierRequestDto requestDto) {
        CaissierResponseDto response = caissierService.creerCaissier(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    private final MecanicienService mecanicienService; // Injection

    @PostMapping("/mecaniciens")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MecanicienResponseDto> creerMecanicien(@Valid @RequestBody MecanicienRequestDto requestDto) {
        return new ResponseEntity<>(mecanicienService.creerMecanicien(requestDto), HttpStatus.CREATED);
    }

}
