package tech.bassoum.malitrans.domain.utilisateur.repository;

import tech.bassoum.malitrans.domain.utilisateur.model.Chauffeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChauffeurRepository extends JpaRepository<Chauffeur, Long> {
    boolean existsByNumeroPermis(String numeroPermis);
    Optional<Chauffeur> findByEmail(String email);
}
