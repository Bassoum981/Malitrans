package tech.bassoum.malitrans.domain.vehicule.repository;

import tech.bassoum.malitrans.domain.vehicule.model.Vehicule;
import tech.bassoum.malitrans.domain.vehicule.enums.StatutVehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
    Optional<Vehicule> findByImmatriculation(String immatriculation);
    List<Vehicule> findByEtat(StatutVehicule etat);
}
