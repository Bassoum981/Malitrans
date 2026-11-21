package tech.bassoum.malitrans.domain.billetterie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.bassoum.malitrans.domain.billetterie.enums.StatutTitreTransport;
import tech.bassoum.malitrans.domain.billetterie.model.TitreTransport;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TitreTransportRepository extends JpaRepository<TitreTransport, Long> {

    Optional<TitreTransport> findByCodeTitre(String codeTitre);

    List<TitreTransport> findByPassagerIdOrderByDateDebutDesc(Long passagerId);

    Optional<TitreTransport> findFirstByPassagerIdAndStatutAndDateFinAfterOrderByDateFinDesc(
            Long passagerId,
            StatutTitreTransport statut,
            LocalDateTime maintenant
    );

    List<TitreTransport> findByStatutAndDateFinBefore(
            StatutTitreTransport statut,
            LocalDateTime maintenant
    );
}
