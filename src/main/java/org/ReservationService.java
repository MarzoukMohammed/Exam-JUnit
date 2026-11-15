package org;

import org.DTO.Commande;
import org.DTO.Etudiant;
import org.DTO.Produit;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ReservationService {

    private static final int DUREE_MAX_JOURS = 7;
    private static final int QUOTA_COMMANDES = 2;

    private final DonneesRepository donneesRepository;

    public ReservationService(DonneesRepository donneesRepository){
        this.donneesRepository = donneesRepository;
    }

    public Commande reserverProduit(
            int idEtudiant,
            int idProduit,
            LocalDate dateDebut,
            LocalDate dateFin
    ) {
        LocalDate aujourdhui = LocalDate.now();

        if (dateDebut == null || dateFin == null)
            throw new IllegalArgumentException("Les dates sont obligatoires");

        if (dateDebut.isBefore(aujourdhui))
            throw new IllegalArgumentException("La date de debut est dans le passe");

        if (dateFin.isBefore(dateDebut))
            throw new IllegalArgumentException("La date de fin precede la date de debut");

        Etudiant etudiant = donneesRepository.trouverEtudiant(idEtudiant)
                .orElseThrow(() -> new IllegalArgumentException("Etudiant inexistant"));
        Produit produit = donneesRepository.trouverProduit(idProduit)
                .orElseThrow(() -> new IllegalArgumentException("Produit inexistant"));

        long duree = ChronoUnit.DAYS.between(dateDebut, dateFin) + 1;
        if (duree > DUREE_MAX_JOURS)
            throw new IllegalArgumentException("Duree maximale depassee");

        long commandesActives = donneesRepository.commandesDeEtudiant(idEtudiant).stream()
                .filter(c -> !c.getDateFin().isBefore(aujourdhui))
                .count();
        if (commandesActives + 1 > QUOTA_COMMANDES)
            throw new IllegalArgumentException("Quota de commandes atteint");

        boolean indisponible = donneesRepository.commandesDeProduit(idProduit).stream()
                .anyMatch(c -> !c.getDateDebut().isAfter(dateFin)
                        && !c.getDateFin().isBefore(dateDebut));
        if (indisponible)
            throw new IllegalArgumentException("Produit indisponible sur la periode");

        Commande commande = new Commande(dateDebut, dateFin, etudiant, produit);

        donneesRepository.enregistrer(commande);
        return commande;
    }
}