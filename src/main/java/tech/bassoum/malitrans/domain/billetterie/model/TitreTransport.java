package tech.bassoum.malitrans.domain.billetterie.model;

import jakarta.persistence.*;
import lombok.*;
import tech.bassoum.malitrans.domain.billetterie.enums.StatutTitreTransport;
import tech.bassoum.malitrans.domain.paiement.model.Paiement;
import tech.bassoum.malitrans.domain.utilisateur.model.Passager;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "titres_transport")
public class TitreTransport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code_titre", unique = true, nullable = false)
    private String codeTitre; // Identifiant unique pour QR Code mobile (ex: PASS-2026-XXXX)

    @Column(name = "date_debut", nullable = false)
    private LocalDateTime dateDebut;

    @Column(name = "date_fin", nullable = false)
    private LocalDateTime dateFin;

    @Column(name = "montant_paye", nullable = false)
    private Double montantPaye; // Sauvegarde le tarif exact payé au moment de la souscription

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutTitreTransport statut;

    // Obligatoirement rattaché à un compte Passager (app mobile)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passager_id", nullable = false)
    private Passager passager;

    // Transaction financière liée à cet abonnement
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paiement_id", unique = true)
    private Paiement paiement;

    @PrePersist
    protected void onCreate() {
        if (this.dateDebut == null) {
            this.dateDebut = LocalDateTime.now();
        }
        if (this.statut == null) {
            this.statut = StatutTitreTransport.ACTIF;
        }
    }
}
