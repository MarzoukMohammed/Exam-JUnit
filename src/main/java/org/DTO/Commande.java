package org.DTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    private static int nombreCommande = 0;

    private Commande(){}

    public Commande(LocalDate dateDebut, LocalDate dateFin, Etudiant etudiant, Produit produit) {
        reference = nombreCommande++;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.etudiant = etudiant;
        this.produit = produit;

        this.duree = (int) ChronoUnit.DAYS.between(dateDebut, dateFin) + 1;
        this.montantLocation = produit.getTarifJournalier()
                .multiply(BigDecimal.valueOf(duree))
                .setScale(2, RoundingMode.HALF_UP);
        this.caution = produit.getCaution().setScale(2, RoundingMode.HALF_UP);
        this.montantTotal = montantLocation.add(caution);
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

//    public BigDecimal getCaution() {
//        return caution;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if(this == obj ) return true;
//        if(obj == null || getClass() != obj.getClass()) return false;
//
//        Commande cmd = (Commande) obj;
//        return (
////                reference == cmd.reference &&
//                dateDebut.equals(cmd.dateDebut) &&
//                        dateFin.equals(cmd.dateFin) &&
//                        montantLocation.equals(cmd.montantLocation) &&
//                        montantTotal.equals(cmd.montantTotal) &&
//                        etudiant.equals(cmd.etudiant) &&
//                        produit.equals(cmd.produit)
//        );
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(
////                reference,
//                dateDebut,
//                dateFin,
//                montantLocation,
//                montantTotal,
//                etudiant,
//                produit
//        );
//    }

}
