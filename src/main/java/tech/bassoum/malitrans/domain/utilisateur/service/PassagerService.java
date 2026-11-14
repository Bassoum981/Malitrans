package tech.bassoum.malitrans.domain.utilisateur.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bassoum.malitrans.domain.utilisateur.dto.PassagerRequestDto;
import tech.bassoum.malitrans.domain.utilisateur.dto.PassagerResponseDto;
import tech.bassoum.malitrans.domain.utilisateur.enums.RoleUtilisateur;
import tech.bassoum.malitrans.domain.utilisateur.enums.StatutUtilisateur;
import tech.bassoum.malitrans.domain.utilisateur.mapper.PassagerMapper;
import tech.bassoum.malitrans.domain.utilisateur.model.Passager;
import tech.bassoum.malitrans.domain.utilisateur.repository.PassagerRepository;
import tech.bassoum.malitrans.domain.utilisateur.repository.UtilisateurRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassagerService {

    private final PassagerRepository passagerRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final PassagerMapper passagerMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public PassagerResponseDto inscrirePassager(PassagerRequestDto requestDto) {
        // 1. Vérifier l'unicité de l'email (tous types d'utilisateurs confondus)
        if (utilisateurRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("Cet email est déjà utilisé par un autre compte.");
        }

        // 2. Convertir le DTO en entité via le mapper
        Passager passager = passagerMapper.toEntity(requestDto);

        // 3. Sécurité et configuration des attributs obligatoires hérités d'Utilisateur
        passager.setMotDePasse(passwordEncoder.encode(requestDto.getMotDePasse()));
        passager.setStatut(StatutUtilisateur.ACTIF); // Statut par défaut à l'inscription
        passager.setRole(RoleUtilisateur.ROLE_PASSAGER); // Rôle figé et sécurisé pour ce service

        // 4. Sauvegarder en base de données
        Passager passagerSauvegarde = passagerRepository.save(passager);

        // 5. Retourner le DTO de réponse
        return passagerMapper.toDto(passagerSauvegarde);
    }

    // --- GET : Récupérer un passager par ID ---
    @Transactional(readOnly = true)
    public PassagerResponseDto recupererPassagerParId(Long id) {
        Passager passager = passagerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Passager introuvable avec l'ID : " + id));
        return passagerMapper.toDto(passager);
    }

    // --- GET : Lister tous les passagers ---
    @Transactional(readOnly = true)
    public List<PassagerResponseDto> listerPassagers() {
        return passagerRepository.findAll().stream()
                .map(passagerMapper::toDto)
                .toList();
    }

    // --- UPDATE : Mettre à jour un passager ---
    @Transactional
    public PassagerResponseDto modifierPassager(Long id, PassagerRequestDto requestDto) {
        Passager passagerExistant = passagerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Passager introuvable avec l'ID : " + id));

        // Vérifier si l'email change et s'il est déjà pris par un autre utilisateur
        if (!passagerExistant.getEmail().equals(requestDto.getEmail())
                && utilisateurRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("Cet email est déjà utilisé par un autre compte.");
        }

        // Mettre à jour les champs modifiables (via les setters ou une méthode du mapper)
        passagerExistant.setNom(requestDto.getNom());
        passagerExistant.setPrenom(requestDto.getPrenom());
        passagerExistant.setEmail(requestDto.getEmail());
        passagerExistant.setTelephone(requestDto.getTelephone());
        passagerExistant.setAdresse(requestDto.getAdresse());
        passagerExistant.setCni(requestDto.getCni());

        // Mettre à jour le mot de passe uniquement s'il est renseigné dans la requête
        if (requestDto.getMotDePasse() != null && !requestDto.getMotDePasse().isBlank()) {
            passagerExistant.setMotDePasse(passwordEncoder.encode(requestDto.getMotDePasse()));
        }

        Passager passagerMisAJour = passagerRepository.save(passagerExistant);
        return passagerMapper.toDto(passagerMisAJour);
    }

    // --- DELETE : Supprimer un passager ---
    @Transactional
    public void supprimerPassager(Long id) {
        if (!passagerRepository.existsById(id)) {
            throw new RuntimeException("Passager introuvable avec l'ID : " + id);
        }
        passagerRepository.deleteById(id);
    }
}
