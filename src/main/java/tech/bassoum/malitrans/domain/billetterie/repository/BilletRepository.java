package tech.bassoum.malitrans.domain.billetterie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.bassoum.malitrans.domain.billetterie.enums.StatutBillet;
import tech.bassoum.malitrans.domain.billetterie.model.Billet;

import java.util.List;
import java.util.Optional;

@Repository
public interface BilletRepository extends JpaRepository<Billet, Long> {

    Optional<Billet> findByNumeroBillet(String numeroBillet);

    List<Billet> findByPassagerIdOrderByDateEmissionDesc(Long passagerId);

    List<Billet> findByAffectationId(Long affectationId);

    boolean existsByAffectationIdAndSiegeIdAndStatutIn(
            Long affectationId,
            Long siegeId,
            List<StatutBillet> statuts
    );
}
