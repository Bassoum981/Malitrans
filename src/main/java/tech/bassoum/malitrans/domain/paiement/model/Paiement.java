package tech.bassoum.malitrans.domain.paiement.model;

import tech.bassoum.malitrans.domain.utilisateur.model.Passager;
import jakarta.persistence.*;
import lombok.*;
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

    @ManyToOne
    @JoinColumn(name = "passager_id", nullable = false)
    private Passager passager;
}
