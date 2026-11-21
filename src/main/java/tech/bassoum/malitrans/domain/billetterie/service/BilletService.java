package tech.bassoum.malitrans.domain.billetterie.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bassoum.malitrans.domain.billetterie.dto.AchatBilletRequestDto;
import tech.bassoum.malitrans.domain.billetterie.dto.BilletResponseDto;
import tech.bassoum.malitrans.domain.billetterie.enums.StatutBillet;
import tech.bassoum.malitrans.domain.billetterie.model.Billet;
import tech.bassoum.malitrans.domain.billetterie.repository.BilletRepository;
import tech.bassoum.malitrans.domain.exploitation.model.Affectation;
import tech.bassoum.malitrans.domain.exploitation.repository.AffectationRepository;
import tech.bassoum.malitrans.domain.paiement.enums.TypePaiement;
import tech.bassoum.malitrans.domain.paiement.model.Paiement;
import tech.bassoum.malitrans.domain.paiement.repository.PaiementRepository;
import tech.bassoum.malitrans.domain.utilisateur.model.Passager;
import tech.bassoum.malitrans.domain.utilisateur.repository.PassagerRepository;
import tech.bassoum.malitrans.domain.vehicule.enums.EtatSiege;
import tech.bassoum.malitrans.domain.vehicule.model.Siege;
import tech.bassoum.malitrans.domain.vehicule.repository.SiegeRepository;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BilletService {

    private final BilletRepository billetRepository;
    private final PassagerRepository passagerRepository;
    private final AffectationRepository affectationRepository;
    private final SiegeRepository siegeRepository;
    private final PaiementRepository paiementRepository;

    @Transactional
    public BilletResponseDto acheterBillet(AchatBilletRequestDto request) {
        // 1. Passager optionnel pour un billet ponctuel
        Passager passager = null;
        if (request.passagerId() != null) {
            passager = passagerRepository.findById(request.passagerId())
                    .orElseThrow(() -> new RuntimeException("Passager introuvable avec l'ID : " + request.passagerId()));
        }

        // 2. Affectation et tarif de la ligne
        Affectation affectation = affectationRepository.findById(request.affectationId())
                .orElseThrow(() -> new RuntimeException("Affectation introuvable avec l'ID : " + request.affectationId()));

        if (affectation.getLigne() == null || affectation.getLigne().getTarif() == null) {
            throw new RuntimeException("Aucune ligne ou aucun tarif n'est défini pour cette affectation.");
        }

        // 3. Siège strictement optionnel en transport urbain (placement libre si null)
        Siege siege = null;
        if (request.siegeId() != null) {
            siege = siegeRepository.findById(request.siegeId())
                    .orElseThrow(() -> new RuntimeException("Siège introuvable avec l'ID : " + request.siegeId()));

            if (affectation.getVehicule() != null && siege.getVehicule() != null
                    && !siege.getVehicule().getId().equals(affectation.getVehicule().getId())) {
                throw new RuntimeException("Ce siège n'appartient pas au bus affecté à ce trajet.");
            }

            if (siege.getEtat() == EtatSiege.HORS_SERVICE) {
                throw new RuntimeException("Ce siège est actuellement hors service.");
            }

            boolean dejaPris = billetRepository.existsByAffectationIdAndSiegeIdAndStatutIn(
                    affectation.getId(),
                    siege.getId(),
                    List.of(StatutBillet.RESERVE, StatutBillet.VALIDE)
            );
            if (dejaPris) {
                throw new RuntimeException("Ce siège est déjà réservé pour ce trajet.");
            }
        }

        // 4. Paiement de type BILLET (sans passager si achat ponctuel anonyme)
        String refPaiement = (request.referencePaiement() != null && !request.referencePaiement().isBlank())
                ? request.referencePaiement()
                : "PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Paiement paiement = Paiement.builder()
                .montant(affectation.getLigne().getTarif())
                .date(LocalDateTime.now())
                .modePaiement(request.modePaiement())
                .reference(refPaiement)
                .statut("Réussi")
                .typePaiement(TypePaiement.BILLET)
                .passager(passager)
                .build();

        paiement = paiementRepository.save(paiement);

        // 5. Émission du billet ponctuel
        Billet billet = Billet.builder()
                .numeroBillet(genererNumeroBillet())
                .dateEmission(LocalDateTime.now())
                .statut(StatutBillet.VALIDE)
                .passager(passager)
                .affectation(affectation)
                .siege(siege)
                .paiement(paiement)
                .build();

        return mapToResponse(billetRepository.save(billet));
    }

    @Transactional
    public BilletResponseDto validerEmbarquement(String numeroBillet) {
        Billet billet = billetRepository.findByNumeroBillet(numeroBillet)
                .orElseThrow(() -> new RuntimeException("Billet invalide ou introuvable : " + numeroBillet));

        if (billet.getStatut() == StatutBillet.UTILISE) {
            throw new RuntimeException("Alerte : Ce billet a déjà été utilisé !");
        }

        if (billet.getStatut() == StatutBillet.ANNULE) {
            throw new RuntimeException("Ce billet a été annulé et n'est plus valable.");
        }

        if (billet.getStatut() != StatutBillet.VALIDE) {
            throw new RuntimeException("Ce billet n'est pas encore payé/validé.");
        }

        billet.setStatut(StatutBillet.UTILISE);
        return mapToResponse(billetRepository.save(billet));
    }

    @Transactional
    public BilletResponseDto annulerBillet(Long billetId) {
        Billet billet = billetRepository.findById(billetId)
                .orElseThrow(() -> new RuntimeException("Billet introuvable."));

        if (billet.getStatut() == StatutBillet.UTILISE) {
            throw new RuntimeException("Impossible d'annuler un billet déjà utilisé.");
        }

        billet.setStatut(StatutBillet.ANNULE);
        return mapToResponse(billetRepository.save(billet));
    }

    @Transactional(readOnly = true)
    public List<BilletResponseDto> getAllBillets() {
        return billetRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BilletResponseDto> getBilletsByPassager(Long passagerId) {
        return billetRepository.findByPassagerIdOrderByDateEmissionDesc(passagerId).stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BilletResponseDto> getBilletsByAffectation(Long affectationId) {
        return billetRepository.findByAffectationId(affectationId).stream()
                .map(this::mapToResponse)
                .toList();
    }

    private String genererNumeroBillet() {
        String randomCode = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "MLT-" + Year.now().getValue() + "-" + randomCode;
    }

    private BilletResponseDto mapToResponse(Billet b) {
        String nomComplet = b.getPassager() != null
                ? b.getPassager().getPrenom() + " " + b.getPassager().getNom()
                : "Passager ponctuel";

        return new BilletResponseDto(
                b.getId(),
                b.getNumeroBillet(),
                b.getDateEmission(),
                b.getStatut(),
                b.getPaiement() != null ? b.getPaiement().getMontant() : 0.0,
                b.getPaiement() != null ? b.getPaiement().getModePaiement() : null,
                b.getPaiement() != null ? b.getPaiement().getReference() : null,
                b.getPassager() != null ? b.getPassager().getId() : null,
                nomComplet,
                b.getAffectation().getId(),
                b.getAffectation().getLigne() != null ? b.getAffectation().getLigne().getNumero() : null,
                b.getAffectation().getLigne() != null ? b.getAffectation().getLigne().getNom() : null,
                b.getAffectation().getDate(),
                b.getAffectation().getHeureDebut(),
                b.getAffectation().getVehicule() != null ? b.getAffectation().getVehicule().getImmatriculation() : null,
                b.getSiege() != null ? b.getSiege().getNumero() : "Placement libre"
        );
    }
}