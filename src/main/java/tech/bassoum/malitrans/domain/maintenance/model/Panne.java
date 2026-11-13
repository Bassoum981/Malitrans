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
@Table(name = "pannes")
public class Panne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String gravite; // Mineure, Majeure, Critique
    private String etat;    // Signalée, En cours, Résolue

    @ManyToOne
    @JoinColumn(name = "vehicule_id", nullable = false)
    private Vehicule vehicule;
}
