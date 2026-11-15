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
        throw new UnsupportedOperationException();
//        BigDecimal zero = new BigDecimal(0);
//        if(tarifJournalier.compareTo(zero) <= 0) throw new IllegalArgumentException();
//        if(caution.compareTo(zero) < 0) throw new IllegalArgumentException();
//        this.id = nombreProduit++;
//        this.libelle = libelle;
//        this.tarifJournalier = tarifJournalier;
//        this.caution = caution;
    }

    public BigDecimal getCaution() {
        return caution;
    }

    public void setCaution(BigDecimal caution) {
        this.caution = caution;
    }

    public BigDecimal getTarifJournalier() {
        return tarifJournalier;
    }

    public void setTarifJournalier(BigDecimal tarifJournalier) {
        this.tarifJournalier = tarifJournalier;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;

        Produit prod = (Produit) obj;

        return (
                id == prod.id &&
                libelle.equals(prod.libelle) &&
                tarifJournalier.equals(prod.tarifJournalier) &&
                caution.equals(prod.caution)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, libelle, tarifJournalier, caution);
    }
}
