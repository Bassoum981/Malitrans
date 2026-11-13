package tech.bassoum.malitrans.domain.utilisateur.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "administrateurs")
public class Administrateur extends Utilisateur {


}
