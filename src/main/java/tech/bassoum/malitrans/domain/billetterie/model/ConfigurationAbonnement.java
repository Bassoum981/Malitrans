package tech.bassoum.malitrans.domain.billetterie.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "configuration_abonnement")
public class ConfigurationAbonnement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String libelle; // ex: "Pass Urbain Mensuel"

    @Column(nullable = false)
    private Double prix; // Modifiable depuis l'admin (ex: 5000.0 FCFA)

    @Column(name = "duree_jours", nullable = false)
    private Integer dureeJours; // ex: 30 jours

    @Column(nullable = false)
    private Boolean actif;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.dateModification = LocalDateTime.now();
        if (this.actif == null) {
            this.actif = true;
        }
    }
}
