package tech.bassoum.malitrans.domain.billetterie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.bassoum.malitrans.domain.billetterie.model.ConfigurationAbonnement;

import java.util.Optional;

@Repository
public interface ConfigurationAbonnementRepository extends JpaRepository<ConfigurationAbonnement, Long> {

    Optional<ConfigurationAbonnement> findFirstByActifTrueOrderByIdDesc();
}
