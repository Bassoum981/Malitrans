package tech.bassoum.malitrans.domain.utilisateur.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Table(name = "employes")
@PrimaryKeyJoinColumn(name = "id")
public class Employe extends Utilisateur {
    @Column(unique = true, nullable = false)
    private String matricule;

    private LocalDate dateEmbauche;

    private String service;

    public void consulterPlanning() {
        // Logique métier à implémenter
    }
}
