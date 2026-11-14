package tech.bassoum.malitrans.domain.utilisateur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.bassoum.malitrans.domain.utilisateur.model.Mecanicien;

public interface MecanicienRepository extends JpaRepository<Mecanicien, Long> {
}
