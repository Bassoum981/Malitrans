package tech.bassoum.malitrans.domain.vehicule.model;

import tech.bassoum.malitrans.domain.vehicule.enums.EtatSiege;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sieges")
public class Siege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String numero; // ex: "A1", "B12"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EtatSiege etat;

    @ManyToOne
    @JoinColumn(name = "vehicule_id", nullable = false)
    private Vehicule vehicule;
}
