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

@Service @RequiredArgsConstructor
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

        return MecanicienResponseDto.builder()
                .id(saved.getId()).nom(saved.getNom()).prenom(saved.getPrenom())
                .email(saved.getEmail()).telephone(saved.getTelephone())
                .matricule(saved.getMatricule()).service(saved.getService())
                .dateEmbauche(saved.getDateEmbauche()).salaire(saved.getSalaire())
                .specialite(saved.getSpecialite()).statut(saved.getStatut()).role(saved.getRole())
                .build();
    }
}
