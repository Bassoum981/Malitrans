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

    private String numeroPermis;
    private String categoriePermis;
    private LocalDate dateExpirationPermis;
    private LocalDate dateVisiteMedicale;
    private Double salaire;
}
