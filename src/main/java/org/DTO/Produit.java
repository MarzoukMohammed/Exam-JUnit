package org.DTO;

import java.math.BigDecimal;
import java.util.Objects;

public class Produit {
    private int id;
    private String libelle;
    private BigDecimal tarifJournalier;
    private BigDecimal caution;

    private Produit(){}

    private static int nombreProduit = 0;

    public Produit(
            String libelle,
            BigDecimal tarifJournalier,
            BigDecimal caution
    ) {
        if (tarifJournalier == null || tarifJournalier.signum() <= 0)
            throw new IllegalArgumentException("Le tarif journalier doit etre strictement positif");
        if (caution == null || caution.signum() < 0)
            throw new IllegalArgumentException("La caution doit etre positive ou nulle");
        this.id = nombreProduit++;
        this.libelle = libelle;
        this.tarifJournalier = tarifJournalier;
        this.caution = caution;
    }

    public BigDecimal getCaution() {
        return caution;
    }


    public BigDecimal getTarifJournalier() {
        return tarifJournalier;
    }

    public int getId() {
        return id;
    }


//    @Override
//    public boolean equals(Object obj) {
//        if(this == obj) return true;
//        if(obj == null || getClass() != obj.getClass()) return false;
//
//        Produit prod = (Produit) obj;
//
//        return (
//                id == prod.id &&
//                libelle.equals(prod.libelle) &&
//                tarifJournalier.equals(prod.tarifJournalier) &&
//                caution.equals(prod.caution)
//        );
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, libelle, tarifJournalier, caution);
//    }
}
