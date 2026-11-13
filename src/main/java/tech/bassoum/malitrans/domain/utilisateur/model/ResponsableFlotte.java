package tech.bassoum.malitrans.domain.utilisateur.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "responsables_flotte")
@PrimaryKeyJoinColumn(name = "id")
public class ResponsableFlotte extends Employe {
    private String grade;
}
