package tech.bassoum.malitrans.domain.utilisateur.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.bassoum.malitrans.domain.utilisateur.dto.*;
import tech.bassoum.malitrans.domain.utilisateur.service.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/employes")
@RequiredArgsConstructor
public class EmployeAdminApi {

    private final ChauffeurService chauffeurService;
    private final CaissierService caissierService;
    private final MecanicienService mecanicienService;
    private final ResponsableFlotteService responsableFlotteService;
    private final AgentExploitationService agentExploitationService;

    // ==========================================
    // CHAUFFEURS (CRUD Complet)
    // ==========================================
    @PostMapping("/chauffeurs")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ChauffeurResponseDto> creerChauffeur(@Valid @RequestBody ChauffeurRequestDto requestDto) {
        return new ResponseEntity<>(chauffeurService.creerChauffeur(requestDto), HttpStatus.CREATED);
    }

    @GetMapping("/chauffeurs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ChauffeurResponseDto>> listerChauffeurs() {
        return ResponseEntity.ok(chauffeurService.listerTous());
    }

    @GetMapping("/chauffeurs/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChauffeurResponseDto> obtenirChauffeurParId(@PathVariable Long id) {
        return ResponseEntity.ok(chauffeurService.recupererParId(id));
    }

    @PutMapping("/chauffeurs/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChauffeurResponseDto> modifierChauffeur(@PathVariable Long id, @Valid @RequestBody ChauffeurRequestDto requestDto) {
        return ResponseEntity.ok(chauffeurService.modifierChauffeur(id, requestDto));
    }

    @DeleteMapping("/chauffeurs/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> supprimerChauffeur(@PathVariable Long id) {
        chauffeurService.supprimerChauffeur(id);
        return ResponseEntity.noContent().build();
    }


    // ==========================================
    // CAISSIERS (CRUD Complet)
    // ==========================================
    @PostMapping("/caissiers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CaissierResponseDto> creerCaissier(@Valid @RequestBody CaissierRequestDto requestDto) {
        return new ResponseEntity<>(caissierService.creerCaissier(requestDto), HttpStatus.CREATED);
    }

    @GetMapping("/caissiers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CaissierResponseDto>> listerCaissiers() {
        return ResponseEntity.ok(caissierService.listerTous());
    }

    @GetMapping("/caissiers/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CaissierResponseDto> obtenirCaissierParId(@PathVariable Long id) {
        return ResponseEntity.ok(caissierService.recupererParId(id));
    }

    @PutMapping("/caissiers/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CaissierResponseDto> modifierCaissier(@PathVariable Long id, @Valid @RequestBody CaissierRequestDto requestDto) {
        return ResponseEntity.ok(caissierService.modifierCaissier(id, requestDto));
    }

    @DeleteMapping("/caissiers/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> supprimerCaissier(@PathVariable Long id) {
        caissierService.supprimerCaissier(id);
        return ResponseEntity.noContent().build();
    }


    // ==========================================
    // MÉCANICIENS (CRUD Complet)
    // ==========================================
    @PostMapping("/mecaniciens")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MecanicienResponseDto> creerMecanicien(@Valid @RequestBody MecanicienRequestDto requestDto) {
        return new ResponseEntity<>(mecanicienService.creerMecanicien(requestDto), HttpStatus.CREATED);
    }

    @GetMapping("/mecaniciens")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MecanicienResponseDto>> listerMecaniciens() {
        return ResponseEntity.ok(mecanicienService.listerTous());
    }

    @GetMapping("/mecaniciens/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MecanicienResponseDto> obtenirMecanicienParId(@PathVariable Long id) {
        return ResponseEntity.ok(mecanicienService.recupererParId(id));
    }

    @PutMapping("/mecaniciens/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MecanicienResponseDto> modifierMecanicien(@PathVariable Long id, @Valid @RequestBody MecanicienRequestDto requestDto) {
        return ResponseEntity.ok(mecanicienService.modifierMecanicien(id, requestDto));
    }

    @DeleteMapping("/mecaniciens/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> supprimerMecanicien(@PathVariable Long id) {
        mecanicienService.supprimerMecanicien(id);
        return ResponseEntity.noContent().build();
    }


    // ==========================================
    // RESPONSABLES DE FLOTTE (CRUD Complet)
    // ==========================================
    @PostMapping("/responsables-flotte")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponsableFlotteResponseDto> creerResponsableFlotte(@Valid @RequestBody ResponsableFlotteRequestDto requestDto) {
        return new ResponseEntity<>(responsableFlotteService.creerResponsableFlotte(requestDto), HttpStatus.CREATED);
    }

    @GetMapping("/responsables-flotte")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ResponsableFlotteResponseDto>> listerResponsablesFlotte() {
        return ResponseEntity.ok(responsableFlotteService.listerTous());
    }

    @GetMapping("/responsables-flotte/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponsableFlotteResponseDto> obtenirResponsableFlotteParId(@PathVariable Long id) {
        return ResponseEntity.ok(responsableFlotteService.recupererParId(id));
    }

    @PutMapping("/responsables-flotte/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponsableFlotteResponseDto> modifierResponsableFlotte(@PathVariable Long id, @Valid @RequestBody ResponsableFlotteRequestDto requestDto) {
        return ResponseEntity.ok(responsableFlotteService.modifierResponsableFlotte(id, requestDto));
    }

    @DeleteMapping("/responsables-flotte/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> supprimerResponsableFlotte(@PathVariable Long id) {
        responsableFlotteService.supprimerResponsableFlotte(id);
        return ResponseEntity.noContent().build();
    }


    // ==========================================
    // AGENTS D'EXPLOITATION (CRUD Complet)
    // ==========================================
    @PostMapping("/agents-exploitation")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AgentExploitationResponseDto> creerAgentExploitation(@Valid @RequestBody AgentExploitationRequestDto requestDto) {
        return new ResponseEntity<>(agentExploitationService.creerAgentExploitation(requestDto), HttpStatus.CREATED);
    }

    @GetMapping("/agents-exploitation")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AgentExploitationResponseDto>> listerAgentsExploitation() {
        return ResponseEntity.ok(agentExploitationService.listerTous());
    }

    @GetMapping("/agents-exploitation/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AgentExploitationResponseDto> obtenirAgentExploitationParId(@PathVariable Long id) {
        return ResponseEntity.ok(agentExploitationService.recupererParId(id));
    }

    @PutMapping("/agents-exploitation/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AgentExploitationResponseDto> modifierAgentExploitation(@PathVariable Long id, @Valid @RequestBody AgentExploitationRequestDto requestDto) {
        return ResponseEntity.ok(agentExploitationService.modifierAgentExploitation(id, requestDto));
    }

    @DeleteMapping("/agents-exploitation/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> supprimerAgentExploitation(@PathVariable Long id) {
        agentExploitationService.supprimerAgentExploitation(id);
        return ResponseEntity.noContent().build();
    }
}
