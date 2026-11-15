package org.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Commande {
    private int reference;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private int duree;
    private BigDecimal montantLocation;
    private BigDecimal montantTotal;
    private BigDecimal caution;

    private Etudiant etudiant;
    private Produit produit;

    private Commande(){}

    public Commande(
            LocalDate dateDebut,
            LocalDate dateFin,
            Etudiant etudiant,
            Produit produit
    ) {
//        this.reference = reference;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.etudiant = etudiant;
        this.produit = produit;

        // TODO: calculer duree, montant location, montant total
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj ) return true;
        if(obj == null || getClass() != obj.getClass()) return false;

        Commande cmd = (Commande) obj;
        return (
//                reference == cmd.reference &&
                dateDebut.equals(cmd.dateDebut) &&
                dateFin.equals(cmd.dateFin) &&
                montantLocation.equals(cmd.montantLocation) &&
                montantTotal.equals(cmd.montantTotal) &&
                etudiant.equals(cmd.etudiant) &&
                produit.equals(cmd.produit)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
//                reference,
                dateDebut,
                dateFin,
                montantLocation,
                montantTotal,
                etudiant,
                produit
        );
    }

    public int getReference(){
        return this.reference;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public Produit getProduit() {
        return produit;
    }


    public int getDuree() {
        return duree;
    }

    public BigDecimal getMontantLocation() {
        return montantLocation;
    }

    public BigDecimal getMontantTotal() {
        return montantTotal;
    }

    public BigDecimal getCaution() {
        return caution;
    }
}
