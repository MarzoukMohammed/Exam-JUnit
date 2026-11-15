package org;

import org.DTO.Commande;
import org.DTO.Etudiant;
import org.DTO.Produit;

import java.util.*;

public class DonneesRepository {

    private final List<Etudiant> etudiants;
    private final List<Produit> produits;
    private final Map<Integer, List<Commande>> commandesParEtudiant = new HashMap<>();
    private final Map<Integer, List<Commande>> commandesParProduit = new HashMap<>();


    public DonneesRepository(List<Etudiant> etudiants, List<Produit> produits) {
        this.etudiants = etudiants;
        this.produits = produits;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public List<Etudiant> getEtudiants() {
        return etudiants;
    }

    public Optional<Etudiant> trouverEtudiant(int id) {
        return etudiants.stream().filter(e -> e.getId() == id).findFirst();
    }

    public Optional<Produit> trouverProduit(int id) {
        return produits.stream().filter(p -> p.getId() == id).findFirst();
    }

    public List<Commande> commandesDeEtudiant(int idEtudiant) {
        return commandesParEtudiant.getOrDefault(idEtudiant, List.of());
    }

    public List<Commande> commandesDeProduit(int idProduit) {
        return commandesParProduit.getOrDefault(idProduit, List.of());
    }

    public void enregistrer(Commande c) {
        commandesParEtudiant.computeIfAbsent(c.getEtudiant().getId(), k -> new ArrayList<>()).add(c);
        commandesParProduit.computeIfAbsent(c.getProduit().getId(), k -> new ArrayList<>()).add(c);
    }
}
