package tech.bassoum.malitrans.domain.vehicule.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.bassoum.malitrans.domain.vehicule.model.Vehicule;
import tech.bassoum.malitrans.domain.vehicule.service.VehiculeService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/vehicules")
@RequiredArgsConstructor
public class VehiculeApi {

    private final VehiculeService vehiculeService;

    @GetMapping
    public ResponseEntity<List<Vehicule>> getAllVehicules() {
        return ResponseEntity.ok(vehiculeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicule> getVehiculeById(@PathVariable Long id) {
        return vehiculeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Vehicule> createVehicule(@Valid @RequestBody Vehicule vehicule) {
        Vehicule saved = vehiculeService.save(vehicule);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicule(@PathVariable Long id) {
        if (vehiculeService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        vehiculeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
