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
@Table(name = "caissiers")
@PrimaryKeyJoinColumn(name = "id")
public class Caissier extends Employe {
    private String caisseId;
    private String zone;
}
