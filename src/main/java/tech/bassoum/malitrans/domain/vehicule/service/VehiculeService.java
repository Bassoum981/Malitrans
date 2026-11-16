package tech.bassoum.malitrans.domain.vehicule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bassoum.malitrans.domain.vehicule.model.Vehicule;
import tech.bassoum.malitrans.domain.vehicule.repository.VehiculeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehiculeService {

    private final VehiculeRepository vehiculeRepository;

    public List<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }

    public Optional<Vehicule> findById(Long id) {
        return vehiculeRepository.findById(id);
    }

    @Transactional
    public Vehicule save(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    @Transactional
    public void delete(Long id) {
        vehiculeRepository.deleteById(id);
    }
}
