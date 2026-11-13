package tech.bassoum.malitrans.shared.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tech.bassoum.malitrans.domain.utilisateur.enums.RoleUtilisateur;
import tech.bassoum.malitrans.domain.utilisateur.enums.StatutUtilisateur;
import tech.bassoum.malitrans.domain.utilisateur.model.Administrateur;
import tech.bassoum.malitrans.domain.utilisateur.repository.UtilisateurRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        String adminEmail = "admin@malitrans.ml";

        // Vérifie si l'administrateur par défaut existe déjà pour éviter les doublons au redémarrage
        if (!utilisateurRepository.existsByEmail(adminEmail)) {
            Administrateur admin = new Administrateur();
            admin.setNom("Bassoum");
            admin.setPrenom("Ibrahim");
            admin.setEmail(adminEmail);
            admin.setTelephone("+22394184125");
            admin.setMotDePasse(passwordEncoder.encode("Admin123!"));
            admin.setRole(RoleUtilisateur.ROLE_ADMIN);
            admin.setStatut(StatutUtilisateur.ACTIF);

            utilisateurRepository.save(admin);
            log.info("✅ Administrateur par défaut créé avec succès : {} / Admin123!", adminEmail);
        } else {
            log.info("ℹ️ L'administrateur par défaut existe déjà.");
        }
    }
}
