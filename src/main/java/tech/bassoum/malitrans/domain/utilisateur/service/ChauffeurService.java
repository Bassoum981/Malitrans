package tech.bassoum.malitrans.domain.utilisateur.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bassoum.malitrans.domain.utilisateur.dto.ChauffeurRequestDto;
import tech.bassoum.malitrans.domain.utilisateur.dto.ChauffeurResponseDto;
import tech.bassoum.malitrans.domain.utilisateur.enums.RoleUtilisateur;
import tech.bassoum.malitrans.domain.utilisateur.enums.StatutUtilisateur;
import tech.bassoum.malitrans.domain.utilisateur.model.Chauffeur;
import tech.bassoum.malitrans.domain.utilisateur.repository.ChauffeurRepository;
import tech.bassoum.malitrans.domain.utilisateur.repository.UtilisateurRepository;

@Service
@RequiredArgsConstructor
public class ChauffeurService {

    private final ChauffeurRepository chauffeurRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ChauffeurResponseDto creerChauffeur(ChauffeurRequestDto requestDto) {
        // 1. Vérification de l'unicité de l'email
        if (utilisateurRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("Cet email est déjà utilisé par un autre compte.");
        }

        // 2. Vérification de l'unicité du numéro de permis
        if (chauffeurRepository.existsByNumeroPermis(requestDto.getNumeroPermis())) {
            throw new IllegalArgumentException("Ce numéro de permis est déjà enregistré.");
        }

        // 3. Instanciation classique par les setters (contourne les problèmes de SuperBuilder)
        Chauffeur chauffeur = new Chauffeur();
        chauffeur.setNom(requestDto.getNom());
        chauffeur.setPrenom(requestDto.getPrenom());
        chauffeur.setEmail(requestDto.getEmail());
        chauffeur.setTelephone(requestDto.getTelephone());
        chauffeur.setMotDePasse(passwordEncoder.encode(requestDto.getMotDePasse()));
        chauffeur.setStatut(StatutUtilisateur.ACTIF);
        chauffeur.setRole(RoleUtilisateur.ROLE_CHAUFFEUR); // Forcé par le serveur !

        // Attributs Employe
        chauffeur.setMatricule(requestDto.getMatricule());
        chauffeur.setDateEmbauche(requestDto.getDateEmbauche() != null ? requestDto.getDateEmbauche() : java.time.LocalDate.now());
        chauffeur.setService(requestDto.getService());

        // Attributs Chauffeur
        chauffeur.setNumeroPermis(requestDto.getNumeroPermis());
        chauffeur.setCategoriePermis(requestDto.getCategoriePermis());
        chauffeur.setDateExpirationPermis(requestDto.getDateExpirationPermis());
        chauffeur.setDateVisiteMedicale(requestDto.getDateVisiteMedicale());
        chauffeur.setSalaire(requestDto.getSalaire());

        // 4. Sauvegarde en base de données
        Chauffeur chauffeurSauvegarde = chauffeurRepository.save(chauffeur);

        // 5. Conversion et retour du DTO de réponse
        return mapToDto(chauffeurSauvegarde);
    }

    private ChauffeurResponseDto mapToDto(Chauffeur chauffeur) {
        return ChauffeurResponseDto.builder()
                .id(chauffeur.getId())
                .nom(chauffeur.getNom())
                .prenom(chauffeur.getPrenom())
                .email(chauffeur.getEmail())
                .telephone(chauffeur.getTelephone())
                .matricule(chauffeur.getMatricule())
                .service(chauffeur.getService())
                .dateEmbauche(chauffeur.getDateEmbauche())
                .numeroPermis(chauffeur.getNumeroPermis())
                .categoriePermis(chauffeur.getCategoriePermis())
                .dateExpirationPermis(chauffeur.getDateExpirationPermis())
                .dateVisiteMedicale(chauffeur.getDateVisiteMedicale())
                .salaire(chauffeur.getSalaire())
                .statut(chauffeur.getStatut())
                .role(chauffeur.getRole())
                .build();
    }
}
