package tech.bassoum.malitrans.domain.utilisateur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.bassoum.malitrans.domain.utilisateur.model.Caissier;

import java.util.Optional;

@Repository
public interface CaissierRepository extends JpaRepository<Caissier, Long> {
    boolean existsByCaisseId(String caisseId);
    Optional<Caissier> findByEmail(String email);
}
