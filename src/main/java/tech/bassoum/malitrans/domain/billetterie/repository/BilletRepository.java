package tech.bassoum.malitrans.domain.billetterie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.bassoum.malitrans.domain.billetterie.enums.StatutBillet;
import tech.bassoum.malitrans.domain.billetterie.model.Billet;

import java.util.List;
import java.util.Optional;

public interface BilletRepository extends JpaRepository<Billet, Long> {
    Optional<Billet> findByNumeroBillet(String numeroBillet);

    // Historique des billets d'un passager (pour l'app mobile Flutter)
    List<Billet> findByPassagerIdOrderByDateEmissionDesc(Long passagerId);

    // Liste des passagers / billets sur une affectation donnée (pour le dashboard Admin)
    List<Billet> findByAffectationId(Long affectationId);

    // Vérifier si un siège est déjà pris sur une affectation précise
    boolean existsByAffectationIdAndSiegeIdAndStatutIn(
            Long affectationId,
            Long siegeId,
            List<StatutBillet> statutsActifs
    );
}
