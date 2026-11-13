package tech.bassoum.malitrans.domain.utilisateur.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "chauffeurs")
@PrimaryKeyJoinColumn(name = "id")
public class Chauffeur extends Employe {

    @Column(nullable = false, unique = true)
    private String numeroPermis;

    @Column(nullable = false)
    private String categoriePermis; // Ex: A, B, C, D, etc.

    @Column(nullable = false)
    private LocalDate dateExpirationPermis;

    private LocalDate dateVisiteMedicale;

    @Column(nullable = false)
    private Double salaire;
}
