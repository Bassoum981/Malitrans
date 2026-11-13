package tech.bassoum.malitrans.domain.maintenance.model;

import tech.bassoum.malitrans.domain.vehicule.model.Vehicule;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "maintenances")
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private String type; // Préventive, Curative

    @Column(columnDefinition = "TEXT")
    private String description;

    private Double cout;
    private Double kilometrage;

    @ManyToOne
    @JoinColumn(name = "vehicule_id", nullable = false)
    private Vehicule vehicule;
}
