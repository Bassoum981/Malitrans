package tech.bassoum.malitrans.domain.utilisateur.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bassoum.malitrans.domain.utilisateur.dto.ResponsableFlotteRequestDto;
import tech.bassoum.malitrans.domain.utilisateur.dto.ResponsableFlotteResponseDto;
import tech.bassoum.malitrans.domain.utilisateur.enums.RoleUtilisateur;
import tech.bassoum.malitrans.domain.utilisateur.enums.StatutUtilisateur;
import tech.bassoum.malitrans.domain.utilisateur.model.ResponsableFlotte;
import tech.bassoum.malitrans.domain.utilisateur.repository.ResponsableFlotteRepository;
import tech.bassoum.malitrans.domain.utilisateur.repository.UtilisateurRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResponsableFlotteService {

    private final ResponsableFlotteRepository responsableFlotteRepository;
    private final UtilisateurRepository utilisateurRepository; // Indispensable pour vérifier l'unicité globale de l'email
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponsableFlotteResponseDto creerResponsableFlotte(ResponsableFlotteRequestDto dto) {
        // 1. Vérifier l'unicité de l'email (tous types d'utilisateurs confondus)
        if (utilisateurRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Cet email est déjà utilisé par un autre compte.");
        }

        // 2. Création de l'entité via le SuperBuilder
        ResponsableFlotte responsable = ResponsableFlotte.builder()
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .email(dto.getEmail())
                .telephone(dto.getTelephone())
                .motDePasse(passwordEncoder.encode(dto.getMotDePasse()))
                .matricule(dto.getMatricule())
                .dateEmbauche(dto.getDateEmbauche())
                .service(dto.getService())
                .salaire(dto.getSalaire())
                .grade(dto.getGrade())
                .statut(StatutUtilisateur.ACTIF) // Par défaut actif
                .role(RoleUtilisateur.RESPONSABLE_FLOTTE) // Assurez-vous d'avoir ce rôle dans votre Enum
                .build();

        ResponsableFlotte saved = responsableFlotteRepository.save(responsable);

        return mapToResponseDto(saved);
    }

    // --- GET : Récupérer par ID ---
    @Transactional(readOnly = true)
    public ResponsableFlotteResponseDto recupererParId(Long id) {
        ResponsableFlotte responsable = responsableFlotteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Responsable de flotte introuvable avec l'ID : " + id));
        return mapToResponseDto(responsable);
    }

    // --- GET : Lister tous les responsables de flotte ---
    @Transactional(readOnly = true)
    public List<ResponsableFlotteResponseDto> listerTous() {
        return responsableFlotteRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    // --- UPDATE : Mettre à jour ---
    @Transactional
    public ResponsableFlotteResponseDto modifierResponsableFlotte(Long id, ResponsableFlotteRequestDto dto) {
        ResponsableFlotte responsableExistant = responsableFlotteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Responsable de flotte introuvable avec l'ID : " + id));

        // Vérifier si l'email change et s'il est déjà pris par un autre utilisateur
        if (!responsableExistant.getEmail().equals(dto.getEmail())
                && utilisateurRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Cet email est déjà utilisé par un autre compte.");
        }

        responsableExistant.setNom(dto.getNom());
        responsableExistant.setPrenom(dto.getPrenom());
        responsableExistant.setEmail(dto.getEmail());
        responsableExistant.setTelephone(dto.getTelephone());

        // Mettre à jour le mot de passe s'il est fourni
        if (dto.getMotDePasse() != null && !dto.getMotDePasse().isBlank()) {
            responsableExistant.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));
        }

        responsableExistant.setMatricule(dto.getMatricule());
        responsableExistant.setDateEmbauche(dto.getDateEmbauche());
        responsableExistant.setService(dto.getService());
        responsableExistant.setSalaire(dto.getSalaire());
        responsableExistant.setGrade(dto.getGrade());

        ResponsableFlotte updated = responsableFlotteRepository.save(responsableExistant);
        return mapToResponseDto(updated);
    }

    // --- DELETE : Supprimer ---
    @Transactional
    public void supprimerResponsableFlotte(Long id) {
        if (!responsableFlotteRepository.existsById(id)) {
            throw new RuntimeException("Responsable de flotte introuvable avec l'ID : " + id);
        }
        responsableFlotteRepository.deleteById(id);
    }

    // --- Méthode utilitaire de mapping vers le ResponseDto ---
    private ResponsableFlotteResponseDto mapToResponseDto(ResponsableFlotte entity) {
        return ResponsableFlotteResponseDto.builder()
                .id(entity.getId())
                .nom(entity.getNom())
                .prenom(entity.getPrenom())
                .email(entity.getEmail())
                .telephone(entity.getTelephone())
                .matricule(entity.getMatricule())
                .dateEmbauche(entity.getDateEmbauche())
                .service(entity.getService())
                .salaire(entity.getSalaire())
                .grade(entity.getGrade())
                .statut(entity.getStatut())
                .role(entity.getRole())
                .build();
    }
}
