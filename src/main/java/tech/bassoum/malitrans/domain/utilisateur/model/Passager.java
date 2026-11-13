package tech.bassoum.malitrans.domain.utilisateur.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Table(name = "passagers")
@PrimaryKeyJoinColumn(name = "id")
public class Passager extends Utilisateur{
    private String adresse;

    @Column(unique = true)
    private String cni;
}
