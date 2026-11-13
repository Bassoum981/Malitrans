package tech.bassoum.malitrans.domain.utilisateur.dto;

import lombok.Data;

@Data
public class PassagerRequestDto {
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String motDePasse;
    private String adresse;
    private String cni;
}
