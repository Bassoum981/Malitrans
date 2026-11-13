package tech.bassoum.malitrans.domain.utilisateur.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bassoum.malitrans.domain.utilisateur.dto.PassagerRequestDto;
import tech.bassoum.malitrans.domain.utilisateur.dto.PassagerResponseDto;
import tech.bassoum.malitrans.domain.utilisateur.mapper.PassagerMapper;
import tech.bassoum.malitrans.domain.utilisateur.model.Passager;
import tech.bassoum.malitrans.domain.utilisateur.repository.PassagerRepository;
import tech.bassoum.malitrans.domain.utilisateur.repository.UtilisateurRepository;

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

        // 3. Hasher le mot de passe pour la sécurité
        passager.setMotDePasse(passwordEncoder.encode(requestDto.getMotDePasse()));

        // 4. Sauvegarder en base de données
        Passager passagerSauvegarde = passagerRepository.save(passager);

        // 5. Retourner le DTO de réponse
        return passagerMapper.toDto(passagerSauvegarde);
    }
}
