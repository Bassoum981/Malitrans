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

import java.time.LocalDate;
import java.util.List;

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

        // 3. Instanciation classique par les setters
        Chauffeur chauffeur = new Chauffeur();
        chauffeur.setNom(requestDto.getNom());
        chauffeur.setPrenom(requestDto.getPrenom());
        chauffeur.setEmail(requestDto.getEmail());
        chauffeur.setTelephone(requestDto.getTelephone());
        chauffeur.setMotDePasse(passwordEncoder.encode(requestDto.getMotDePasse()));
        chauffeur.setStatut(StatutUtilisateur.ACTIF);
        chauffeur.setRole(RoleUtilisateur.ROLE_CHAUFFEUR);

        // Attributs Employe
        chauffeur.setMatricule(requestDto.getMatricule());
        chauffeur.setDateEmbauche(requestDto.getDateEmbauche() != null ? requestDto.getDateEmbauche() : LocalDate.now());
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

    // --- GET : Récupérer un chauffeur par ID ---
    @Transactional(readOnly = true)
    public ChauffeurResponseDto recupererParId(Long id) {
        Chauffeur chauffeur = chauffeurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chauffeur introuvable avec l'ID : " + id));
        return mapToDto(chauffeur);
    }

    // --- GET : Lister tous les chauffeurs ---
    @Transactional(readOnly = true)
    public List<ChauffeurResponseDto> listerTous() {
        return chauffeurRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    // --- UPDATE : Mettre à jour un chauffeur ---
    @Transactional
    public ChauffeurResponseDto modifierChauffeur(Long id, ChauffeurRequestDto requestDto) {
        Chauffeur chauffeurExistant = chauffeurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chauffeur introuvable avec l'ID : " + id));

        // Vérifier l'unicité de l'email s'il change
        if (!chauffeurExistant.getEmail().equals(requestDto.getEmail())
                && utilisateurRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("Cet email est déjà utilisé par un autre compte.");
        }

        // Vérifier l'unicité du numéro de permis s'il change
        if (!chauffeurExistant.getNumeroPermis().equals(requestDto.getNumeroPermis())
                && chauffeurRepository.existsByNumeroPermis(requestDto.getNumeroPermis())) {
            throw new IllegalArgumentException("Ce numéro de permis est déjà enregistré par un autre chauffeur.");
        }

        chauffeurExistant.setNom(requestDto.getNom());
        chauffeurExistant.setPrenom(requestDto.getPrenom());
        chauffeurExistant.setEmail(requestDto.getEmail());
        chauffeurExistant.setTelephone(requestDto.getTelephone());

        // Mettre à jour le mot de passe s'il est fourni
        if (requestDto.getMotDePasse() != null && !requestDto.getMotDePasse().isBlank()) {
            chauffeurExistant.setMotDePasse(passwordEncoder.encode(requestDto.getMotDePasse()));
        }

        chauffeurExistant.setMatricule(requestDto.getMatricule());
        if (requestDto.getDateEmbauche() != null) {
            chauffeurExistant.setDateEmbauche(requestDto.getDateEmbauche());
        }
        chauffeurExistant.setService(requestDto.getService());

        chauffeurExistant.setNumeroPermis(requestDto.getNumeroPermis());
        chauffeurExistant.setCategoriePermis(requestDto.getCategoriePermis());
        chauffeurExistant.setDateExpirationPermis(requestDto.getDateExpirationPermis());
        chauffeurExistant.setDateVisiteMedicale(requestDto.getDateVisiteMedicale());
        chauffeurExistant.setSalaire(requestDto.getSalaire());

        Chauffeur chauffeurMisAJour = chauffeurRepository.save(chauffeurExistant);
        return mapToDto(chauffeurMisAJour);
    }

    // --- DELETE : Supprimer un chauffeur ---
    @Transactional
    public void supprimerChauffeur(Long id) {
        if (!chauffeurRepository.existsById(id)) {
            throw new RuntimeException("Chauffeur introuvable avec l'ID : " + id);
        }
        chauffeurRepository.deleteById(id);
    }

    // --- Méthode utilitaire de mapping ---
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
