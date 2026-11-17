package tech.bassoum.malitrans.domain.billetterie.model;

import jakarta.persistence.*;
import lombok.*;
import tech.bassoum.malitrans.domain.billetterie.enums.StatutBillet;
import tech.bassoum.malitrans.domain.exploitation.model.Affectation;
import tech.bassoum.malitrans.domain.paiement.model.Paiement;
import tech.bassoum.malitrans.domain.utilisateur.model.Passager;
import tech.bassoum.malitrans.domain.vehicule.model.Siege;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "billets")
public class Billet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_billet", unique = true, nullable = false)
    private String numeroBillet; // Identifiant unique pour QR Code (ex: MLT-2026-XXXX)

    @Column(name = "date_emission", nullable = false)
    private LocalDateTime dateEmission;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutBillet statut;

    // Le passager détenteur du billet
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passager_id", nullable = false)
    private Passager passager;

    // L'affectation (Bus + Chauffeur + Ligne + Horaire)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "affectation_id", nullable = false)
    private Affectation affectation;

    // Le siège réservé (optionnel selon le type de ligne : urbain vs interurbain)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "siege_id", nullable = true)
    private Siege siege;

    // La transaction financière associée
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paiement_id", unique = true)
    private Paiement paiement;

    @PrePersist
    protected void onCreate() {
        if (this.dateEmission == null) {
            this.dateEmission = LocalDateTime.now();
        }
        if (this.statut == null) {
            this.statut = StatutBillet.RESERVE;
        }
    }
}
