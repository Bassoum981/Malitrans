package tech.bassoum.malitrans.domain.billetterie.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bassoum.malitrans.domain.billetterie.dto.SouscriptionTitreRequestDto;
import tech.bassoum.malitrans.domain.billetterie.dto.TitreTransportResponseDto;
import tech.bassoum.malitrans.domain.billetterie.enums.StatutTitreTransport;
import tech.bassoum.malitrans.domain.billetterie.model.ConfigurationAbonnement;
import tech.bassoum.malitrans.domain.billetterie.model.TitreTransport;
import tech.bassoum.malitrans.domain.billetterie.repository.TitreTransportRepository;
import tech.bassoum.malitrans.domain.paiement.enums.TypePaiement;
import tech.bassoum.malitrans.domain.paiement.model.Paiement;
import tech.bassoum.malitrans.domain.paiement.repository.PaiementRepository;
import tech.bassoum.malitrans.domain.utilisateur.model.Passager;
import tech.bassoum.malitrans.domain.utilisateur.repository.PassagerRepository;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TitreTransportService {

    private final TitreTransportRepository titreRepository;
    private final PassagerRepository passagerRepository;
    private final PaiementRepository paiementRepository;
    private final ConfigurationAbonnementService configurationService;

    @Transactional
    public TitreTransportResponseDto souscrireOuRenouveler(SouscriptionTitreRequestDto request) {
        Passager passager = passagerRepository.findById(request.passagerId())
                .orElseThrow(() -> new RuntimeException("Passager introuvable avec l'ID : " + request.passagerId()));

        ConfigurationAbonnement config = configurationService.getConfigurationActive();
        LocalDateTime maintenant = LocalDateTime.now();

        // Si le passager a déjà un titre ACTIF non expiré, on prolonge à partir de sa dateFin
        LocalDateTime dateDebut = titreRepository
                .findFirstByPassagerIdAndStatutAndDateFinAfterOrderByDateFinDesc(
                        passager.getId(),
                        StatutTitreTransport.ACTIF,
                        maintenant
                )
                .map(TitreTransport::getDateFin)
                .orElse(maintenant);

        LocalDateTime dateFin = dateDebut.plusDays(config.getDureeJours());

        String refPaiement = (request.referencePaiement() != null && !request.referencePaiement().isBlank())
                ? request.referencePaiement()
                : "PAY-PASS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Paiement paiement = Paiement.builder()
                .montant(config.getPrix())
                .date(maintenant)
                .modePaiement(request.modePaiement())
                .reference(refPaiement)
                .statut("Réussi")
                .typePaiement(TypePaiement.TITRE_TRANSPORT)
                .passager(passager)
                .build();

        paiement = paiementRepository.save(paiement);

        TitreTransport titre = TitreTransport.builder()
                .codeTitre(genererCodeTitre())
                .dateDebut(dateDebut)
                .dateFin(dateFin)
                .montantPaye(config.getPrix())
                .statut(StatutTitreTransport.ACTIF)
                .passager(passager)
                .paiement(paiement)
                .build();

        return mapToResponse(titreRepository.save(titre));
    }

    /**
     * Contrôle d'un titre de transport : vérifie la validité sans le consommer.
     */
    @Transactional
    public TitreTransportResponseDto verifierTitre(String codeTitre) {
        TitreTransport titre = titreRepository.findByCodeTitre(codeTitre)
                .orElseThrow(() -> new RuntimeException("Titre de transport introuvable : " + codeTitre));

        LocalDateTime maintenant = LocalDateTime.now();

        if (titre.getStatut() == StatutTitreTransport.SUSPENDU) {
            throw new RuntimeException("Titre de transport suspendu.");
        }

        if (maintenant.isAfter(titre.getDateFin())) {
            titre.setStatut(StatutTitreTransport.EXPIRE);
            titreRepository.save(titre);
            throw new RuntimeException("Titre de transport expiré le " + titre.getDateFin());
        }

        return mapToResponse(titre);
    }

    @Transactional(readOnly = true)
    public List<TitreTransportResponseDto> getTitresByPassager(Long passagerId) {
        return titreRepository.findByPassagerIdOrderByDateDebutDesc(passagerId).stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TitreTransportResponseDto> getAllTitres() {
        return titreRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    private String genererCodeTitre() {
        String randomCode = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "PASS-" + Year.now().getValue() + "-" + randomCode;
    }

    private TitreTransportResponseDto mapToResponse(TitreTransport t) {
        LocalDateTime maintenant = LocalDateTime.now();
        boolean estValide = t.getStatut() == StatutTitreTransport.ACTIF
                && !maintenant.isBefore(t.getDateDebut())
                && !maintenant.isAfter(t.getDateFin());

        String nomComplet = t.getPassager() != null
                ? t.getPassager().getPrenom() + " " + t.getPassager().getNom()
                : null;

        return new TitreTransportResponseDto(
                t.getId(),
                t.getCodeTitre(),
                t.getDateDebut(),
                t.getDateFin(),
                t.getMontantPaye(),
                t.getStatut(),
                estValide,
                t.getPassager() != null ? t.getPassager().getId() : null,
                nomComplet,
                t.getPaiement() != null ? t.getPaiement().getModePaiement() : null,
                t.getPaiement() != null ? t.getPaiement().getReference() : null
        );
    }
}
