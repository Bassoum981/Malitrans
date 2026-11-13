package tech.bassoum.malitrans.domain.utilisateur.repository;

import tech.bassoum.malitrans.domain.utilisateur.model.Passager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PassagerRepository extends JpaRepository<Passager, Long> {
    Optional<Passager> findByCni(String cni);
}
