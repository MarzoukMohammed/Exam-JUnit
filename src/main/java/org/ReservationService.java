package org;

import org.DTO.Commande;

import java.time.LocalDate;

public class ReservationService {

    private DonneesRepository donneesRepository ;

    public ReservationService(DonneesRepository donneesRepository){
        this.donneesRepository = donneesRepository;
    }

    public Commande reserverProduit(
            int idEtudiant,
            int idProduit,
            LocalDate dateDebutReservation,
            LocalDate dateFinReservation
    ) {
        throw new UnsupportedOperationException();
    }
}
