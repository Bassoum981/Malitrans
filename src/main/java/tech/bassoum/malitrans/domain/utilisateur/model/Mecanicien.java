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
@Table(name = "mecaniciens")
@PrimaryKeyJoinColumn(name = "id")
public class Mecanicien extends Employe {
    private String specialite;
}
