package tech.bassoum.malitrans.domain.utilisateur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.bassoum.malitrans.domain.utilisateur.model.ResponsableFlotte;

@Repository
public interface ResponsableFlotteRepository extends JpaRepository<ResponsableFlotte, Long> {
}
