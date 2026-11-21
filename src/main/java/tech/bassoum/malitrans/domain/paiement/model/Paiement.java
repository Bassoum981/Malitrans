package tech.bassoum.malitrans.domain.paiement.model;

import jakarta.persistence.*;
import lombok.*;
import tech.bassoum.malitrans.domain.paiement.enums.TypePaiement;
import tech.bassoum.malitrans.domain.utilisateur.model.Passager;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "paiements")
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double montant;

    @Column(nullable = false)
    private LocalDateTime date;

    private String modePaiement; // Orange Money, Moov, Espèces

    @Column(unique = true, nullable = false)
    private String reference;    // Référence unique de transaction

    private String statut;       // Réussi, Échoué, En attente

    @Enumerated(EnumType.STRING)
    @Column(name = "type_paiement", nullable = false)
    private TypePaiement typePaiement; // BILLET ou TITRE_TRANSPORT

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passager_id", nullable = true) // Optionnel pour un billet ponctuel
    private Passager passager;
}
