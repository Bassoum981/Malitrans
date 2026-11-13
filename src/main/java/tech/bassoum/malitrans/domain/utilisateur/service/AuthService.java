package tech.bassoum.malitrans.domain.utilisateur.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import tech.bassoum.malitrans.domain.utilisateur.dto.JwtResponseDto;
import tech.bassoum.malitrans.domain.utilisateur.dto.LoginRequestDto;
import tech.bassoum.malitrans.domain.utilisateur.model.Utilisateur;
import tech.bassoum.malitrans.domain.utilisateur.repository.UtilisateurRepository;
import tech.bassoum.malitrans.shared.security.JwtService;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UtilisateurRepository utilisateurRepository;
    private final JwtService jwtService;

    public JwtResponseDto connecter(LoginRequestDto requestDto) {
        // 1. Authentifier via Spring Security (lève une exception si échec)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getEmail(),
                        requestDto.getMotDePasse()
                )
        );

        // 2. Récupérer l'utilisateur authentifié
        Utilisateur utilisateur = (Utilisateur) authentication.getPrincipal();

        // 3. Générer le token JWT
        String jwtToken = jwtService.generateToken(utilisateur);

        // 4. Retourner la réponse avec le token
        return JwtResponseDto.builder()
                .token(jwtToken)
                .email(utilisateur.getEmail())
                .nomComplet(utilisateur.getPrenom() + " " + utilisateur.getNom())
                .role(utilisateur.getRole())         // On injecte le rôle
                .statut(utilisateur.getStatut())
                .build();
    }
}
