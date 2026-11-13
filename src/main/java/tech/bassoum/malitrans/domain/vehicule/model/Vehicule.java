package tech.bassoum.malitrans.domain.vehicule.model;

import tech.bassoum.malitrans.domain.vehicule.enums.StatutVehicule;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "vehicules")
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroBus;

    @Column(unique = true, nullable = false)
    private String immatriculation;

    private String marque;

    private String modele;

    private Integer annee;

    private Integer capaciteAssise;

    private Integer capaciteDebout;

    private Double kilometrage;

    private Double carburant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutVehicule etat;

    private Boolean gpsActif;

    @ManyToOne
    @JoinColumn(name = "depot_id")
    private Depot depot;

    @OneToMany(mappedBy = "vehicule", cascade = CascadeType.ALL)
    private List<Siege> sieges;
}
