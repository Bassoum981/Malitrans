package tech.bassoum.malitrans.domain.utilisateur.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bassoum.malitrans.domain.utilisateur.dto.MecanicienRequestDto;
import tech.bassoum.malitrans.domain.utilisateur.dto.MecanicienResponseDto;
import tech.bassoum.malitrans.domain.utilisateur.enums.RoleUtilisateur;
import tech.bassoum.malitrans.domain.utilisateur.enums.StatutUtilisateur;
import tech.bassoum.malitrans.domain.utilisateur.model.Mecanicien;
import tech.bassoum.malitrans.domain.utilisateur.repository.MecanicienRepository;
import tech.bassoum.malitrans.domain.utilisateur.repository.UtilisateurRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MecanicienService {

    private final MecanicienRepository mecanicienRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MecanicienResponseDto creerMecanicien(MecanicienRequestDto dto) {
        if (utilisateurRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Cet email est déjà utilisé.");
        }

        Mecanicien mec = new Mecanicien();
        mec.setNom(dto.getNom());
        mec.setPrenom(dto.getPrenom());
        mec.setEmail(dto.getEmail());
        mec.setTelephone(dto.getTelephone());
        mec.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));
        mec.setStatut(StatutUtilisateur.ACTIF);
        mec.setRole(RoleUtilisateur.ROLE_MECANICIEN);

        mec.setMatricule(dto.getMatricule());
        mec.setDateEmbauche(dto.getDateEmbauche() != null ? dto.getDateEmbauche() : LocalDate.now());
        mec.setService(dto.getService());
        mec.setSalaire(dto.getSalaire());
        mec.setSpecialite(dto.getSpecialite());

        Mecanicien saved = mecanicienRepository.save(mec);
        return mapToResponseDto(saved);
    }

    // --- GET : Récupérer par ID ---
    @Transactional(readOnly = true)
    public MecanicienResponseDto recupererParId(Long id) {
        Mecanicien mec = mecanicienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mécanicien introuvable avec l'ID : " + id));
        return mapToResponseDto(mec);
    }

    // --- GET : Lister tous les mécaniciens ---
    @Transactional(readOnly = true)
    public List<MecanicienResponseDto> listerTous() {
        return mecanicienRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    // --- UPDATE : Mettre à jour ---
    @Transactional
    public MecanicienResponseDto modifierMecanicien(Long id, MecanicienRequestDto dto) {
        Mecanicien mec = mecanicienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mécanicien introuvable avec l'ID : " + id));

        // Vérifier l'unicité de l'email s'il est modifié
        if (!mec.getEmail().equals(dto.getEmail()) && utilisateurRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Cet email est déjà utilisé par un autre compte.");
        }

        mec.setNom(dto.getNom());
        mec.setPrenom(dto.getPrenom());
        mec.setEmail(dto.getEmail());
        mec.setTelephone(dto.getTelephone());

        if (dto.getMotDePasse() != null && !dto.getMotDePasse().isBlank()) {
            mec.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));
        }

        mec.setMatricule(dto.getMatricule());
        if (dto.getDateEmbauche() != null) {
            mec.setDateEmbauche(dto.getDateEmbauche());
        }
        mec.setService(dto.getService());
        mec.setSalaire(dto.getSalaire());
        mec.setSpecialite(dto.getSpecialite());

        Mecanicien updated = mecanicienRepository.save(mec);
        return mapToResponseDto(updated);
    }

    // --- DELETE : Supprimer ---
    @Transactional
    public void supprimerMecanicien(Long id) {
        if (!mecanicienRepository.existsById(id)) {
            throw new RuntimeException("Mécanicien introuvable avec l'ID : " + id);
        }
        mecanicienRepository.deleteById(id);
    }

    // --- Méthode utilitaire de mapping ---
    private MecanicienResponseDto mapToResponseDto(Mecanicien mec) {
        return MecanicienResponseDto.builder()
                .id(mec.getId())
                .nom(mec.getNom())
                .prenom(mec.getPrenom())
                .email(mec.getEmail())
                .telephone(mec.getTelephone())
                .matricule(mec.getMatricule())
                .service(mec.getService())
                .dateEmbauche(mec.getDateEmbauche())
                .salaire(mec.getSalaire())
                .specialite(mec.getSpecialite())
                .statut(mec.getStatut())
                .role(mec.getRole())
                .build();
    }
}
