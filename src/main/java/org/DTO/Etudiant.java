package org.DTO;

import java.util.Objects;

public class Etudiant {
    private int id;
    private String nom;
    private String prenom;

    private Etudiant(){}

    private static int nombreEtudiant = 0;

    public Etudiant(String nom, String prenom){
        this.id = nombreEtudiant++;
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj ) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Etudiant etu = (Etudiant) obj;
        return (
                id == etu.id &&
                nom.equals(etu.nom) &&
                prenom.equals(etu.prenom)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, prenom);
    }
}
