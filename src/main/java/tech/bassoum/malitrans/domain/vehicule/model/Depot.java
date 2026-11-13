package tech.bassoum.malitrans.domain.vehicule.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "depots")
public class Depot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String adresse;

    private Integer capacite;

    @OneToMany(mappedBy = "depot", cascade = CascadeType.ALL)
    private List<Vehicule> vehicules;
}
