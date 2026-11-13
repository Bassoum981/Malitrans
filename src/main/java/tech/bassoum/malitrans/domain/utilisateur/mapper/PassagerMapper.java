package tech.bassoum.malitrans.domain.utilisateur.mapper;

import tech.bassoum.malitrans.domain.utilisateur.dto.PassagerRequestDto;
import tech.bassoum.malitrans.domain.utilisateur.dto.PassagerResponseDto;
import tech.bassoum.malitrans.domain.utilisateur.enums.StatutUtilisateur;
import tech.bassoum.malitrans.domain.utilisateur.model.Passager;
import org.springframework.stereotype.Component;

@Component
public class PassagerMapper {

    // Convertit l'Entité JPA vers le DTO de Réponse
    public PassagerResponseDto toDto(Passager passager) {
        if (passager == null) {
            return null;
        }

        return PassagerResponseDto.builder()
                .id(passager.getId())
                .nom(passager.getNom())
                .prenom(passager.getPrenom())
                .email(passager.getEmail())
                .telephone(passager.getTelephone())
                .adresse(passager.getAdresse())
                .cni(passager.getCni())
                .statut(passager.getStatut())
                .build();
    }

    // Convertit le DTO de Requête vers l'Entité JPA
    public Passager toEntity(PassagerRequestDto requestDto) {
        if (requestDto == null) {
            return null;
        }

        Passager passager = new Passager();
        passager.setNom(requestDto.getNom());
        passager.setPrenom(requestDto.getPrenom());
        passager.setEmail(requestDto.getEmail());
        passager.setTelephone(requestDto.getTelephone());
        passager.setMotDePasse(requestDto.getMotDePasse()); // Pensez à le crypter plus tard avec Spring Security !
        passager.setAdresse(requestDto.getAdresse());
        passager.setCni(requestDto.getCni());
        passager.setStatut(StatutUtilisateur.ACTIF); // Statut par défaut à la création

        return passager;
    }
}
