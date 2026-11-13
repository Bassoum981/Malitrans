package tech.bassoum.malitrans.domain.paiement.repository;

import tech.bassoum.malitrans.domain.paiement.model.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    Optional<Paiement> findByReference(String reference);
}
