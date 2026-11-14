package tech.bassoum.malitrans.domain.utilisateur.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bassoum.malitrans.domain.utilisateur.dto.CaissierRequestDto;
import tech.bassoum.malitrans.domain.utilisateur.dto.CaissierResponseDto;
import tech.bassoum.malitrans.domain.utilisateur.enums.RoleUtilisateur;
import tech.bassoum.malitrans.domain.utilisateur.enums.StatutUtilisateur;
import tech.bassoum.malitrans.domain.utilisateur.model.Caissier;
import tech.bassoum.malitrans.domain.utilisateur.repository.CaissierRepository;
import tech.bassoum.malitrans.domain.utilisateur.repository.UtilisateurRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CaissierService {

    private final CaissierRepository caissierRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CaissierResponseDto creerCaissier(CaissierRequestDto requestDto) {
        // 1. Vérification de l'unicité de l'email
        if (utilisateurRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("Cet email est déjà utilisé par un autre compte.");
        }

        // 2. Vérification de l'unicité de la caisse
        if (caissierRepository.existsByCaisseId(requestDto.getCaisseId())) {
            throw new IllegalArgumentException("Cette caisse est déjà assignée à un autre caissier.");
        }

        // 3. Instanciation via les setters (robuste face à l'héritage JPA)
        Caissier caissier = new Caissier();
        caissier.setNom(requestDto.getNom());
        caissier.setPrenom(requestDto.getPrenom());
        caissier.setEmail(requestDto.getEmail());
        caissier.setTelephone(requestDto.getTelephone());
        caissier.setMotDePasse(passwordEncoder.encode(requestDto.getMotDePasse()));
        caissier.setStatut(StatutUtilisateur.ACTIF);
        caissier.setRole(RoleUtilisateur.ROLE_CAISSIER); // Rôle forcé par le serveur !

        // Attributs Employe
        caissier.setMatricule(requestDto.getMatricule());
        caissier.setDateEmbauche(requestDto.getDateEmbauche() != null ? requestDto.getDateEmbauche() : LocalDate.now());
        caissier.setService(requestDto.getService());
        caissier.setSalaire(requestDto.getSalaire());

        // Attributs Caissier
        caissier.setCaisseId(requestDto.getCaisseId());
        caissier.setZone(requestDto.getZone());

        // 4. Sauvegarde en base de données
        Caissier caissierSauvegarde = caissierRepository.save(caissier);

        // 5. Conversion et retour du DTO de réponse
        return mapToDto(caissierSauvegarde);
    }

    // --- GET : Récupérer un caissier par ID ---
    @Transactional(readOnly = true)
    public CaissierResponseDto recupererParId(Long id) {
        Caissier caissier = caissierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Caissier introuvable avec l'ID : " + id));
        return mapToDto(caissier);
    }

    // --- GET : Lister tous les caissiers ---
    @Transactional(readOnly = true)
    public List<CaissierResponseDto> listerTous() {
        return caissierRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    // --- UPDATE : Mettre à jour un caissier ---
    @Transactional
    public CaissierResponseDto modifierCaissier(Long id, CaissierRequestDto requestDto) {
        Caissier caissierExistant = caissierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Caissier introuvable avec l'ID : " + id));

        // Vérifier l'unicité de l'email s'il change
        if (!caissierExistant.getEmail().equals(requestDto.getEmail())
                && utilisateurRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("Cet email est déjà utilisé par un autre compte.");
        }

        // Vérifier l'unicité de la caisse si elle change
        if (!caissierExistant.getCaisseId().equals(requestDto.getCaisseId())
                && caissierRepository.existsByCaisseId(requestDto.getCaisseId())) {
            throw new IllegalArgumentException("Cette caisse est déjà assignée à un autre caissier.");
        }

        caissierExistant.setNom(requestDto.getNom());
        caissierExistant.setPrenom(requestDto.getPrenom());
        caissierExistant.setEmail(requestDto.getEmail());
        caissierExistant.setTelephone(requestDto.getTelephone());

        // Mettre à jour le mot de passe s'il est fourni
        if (requestDto.getMotDePasse() != null && !requestDto.getMotDePasse().isBlank()) {
            caissierExistant.setMotDePasse(passwordEncoder.encode(requestDto.getMotDePasse()));
        }

        caissierExistant.setMatricule(requestDto.getMatricule());
        if (requestDto.getDateEmbauche() != null) {
            caissierExistant.setDateEmbauche(requestDto.getDateEmbauche());
        }
        caissierExistant.setService(requestDto.getService());
        caissierExistant.setSalaire(requestDto.getSalaire());

        caissierExistant.setCaisseId(requestDto.getCaisseId());
        caissierExistant.setZone(requestDto.getZone());

        Caissier caissierMisAJour = caissierRepository.save(caissierExistant);
        return mapToDto(caissierMisAJour);
    }

    // --- DELETE : Supprimer un caissier ---
    @Transactional
    public void supprimerCaissier(Long id) {
        if (!caissierRepository.existsById(id)) {
            throw new RuntimeException("Caissier introuvable avec l'ID : " + id);
        }
        caissierRepository.deleteById(id);
    }

    private CaissierResponseDto mapToDto(Caissier caissier) {
        return CaissierResponseDto.builder()
                .id(caissier.getId())
                .nom(caissier.getNom())
                .prenom(caissier.getPrenom())
                .email(caissier.getEmail())
                .telephone(caissier.getTelephone())
                .matricule(caissier.getMatricule())
                .service(caissier.getService())
                .dateEmbauche(caissier.getDateEmbauche())
                .salaire(caissier.getSalaire())
                .caisseId(caissier.getCaisseId())
                .zone(caissier.getZone())
                .statut(caissier.getStatut())
                .role(caissier.getRole())
                .build();
    }
}
