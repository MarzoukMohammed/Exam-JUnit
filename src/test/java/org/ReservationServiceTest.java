package org;

import org.DTO.Commande;
import org.DTO.Etudiant;
import org.DTO.Produit;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

class ReservationServiceTest {

    private ReservationService reservationService;
    private DonneesRepository dataRepo;

    @BeforeEach
    void init(){
        dataRepo = new DonneesRepository(
                List.of(
                        new Etudiant("mohammed", "marzouk"),
                        new Etudiant("marzouk", "mohammed"),
                        new Etudiant("maizi", "yousra"),
                        new Etudiant("yousra", "maizi"),
                        new Etudiant("soufiane", "marzouk"),
                        new Etudiant("marzouk", "sofiane")
                ) ,
                List.of(
                        new Produit("ordinateur portable", new BigDecimal("13.325"), new BigDecimal(100)),
                        new Produit("video projecteur", new BigDecimal(10), new BigDecimal(100)),
                        new Produit("kits electronique", new BigDecimal(10), new BigDecimal(100))
                )
        );
        reservationService = new ReservationService(dataRepo);
    }

    // RG01 ===============================================================================
    @Test
    void RG01_dateDebutVide() {
        Etudiant etudiant = dataRepo.getEtudiants().getFirst();
        Produit produit = dataRepo.getProduits().getFirst();
        final LocalDate DATE = LocalDate.now().plusDays(2);

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.reserverProduit(etudiant.getId(), produit.getId(), null, DATE)
        );
    }

    @Test
    void RG01_dateFinVide() {
        Etudiant etudiant = dataRepo.getEtudiants().getFirst();
        Produit produit = dataRepo.getProduits().getFirst();
        final LocalDate DATE = LocalDate.now().plusDays(2);

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.reserverProduit(etudiant.getId(), produit.getId(), DATE, null)
        );
    }

    // RG02 ===============================================================================

    @Test
    @DisplayName("Test date debut anterieur a date courant")
    void RG02_dateDebutInferieurACourant() {
        Etudiant etudiant = dataRepo.getEtudiants().getFirst();
        Produit produit = dataRepo.getProduits().getFirst();
        final LocalDate DATE_DEBUT = LocalDate.now().minusDays(3);
        final LocalDate DATE_FIN = DATE_DEBUT.plusDays(4);

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.reserverProduit(etudiant.getId(), produit.getId(), DATE_DEBUT, DATE_FIN)
        );
    }

    @Test
    @DisplayName("Test date debut egual a date courant")
    void RG02_dateDebutEgualACourant() {
        Etudiant etudiant = dataRepo.getEtudiants().getFirst();
        Produit produit = dataRepo.getProduits().getFirst();
        final LocalDate DATE_DEBUT = LocalDate.now();
        final LocalDate DATE_FIN = DATE_DEBUT.plusDays(4);

        Assertions.assertDoesNotThrow(
                () -> reservationService.reserverProduit(etudiant.getId(), produit.getId(), DATE_DEBUT, DATE_FIN).getDateDebut()
        );
    }

    @Test
    @DisplayName("Test date debut posterieur a date courant")
    void RG02_dateDebutSuperieurACourant() {
        Etudiant etudiant = dataRepo.getEtudiants().getFirst();
        Produit produit = dataRepo.getProduits().getFirst();
        final LocalDate DATE_DEBUT = LocalDate.now().plusDays(1);
        final LocalDate DATE_FIN = DATE_DEBUT.plusDays(4);

        Assertions.assertDoesNotThrow(
                () -> reservationService.reserverProduit(etudiant.getId(), produit.getId(), DATE_DEBUT, DATE_FIN).getDateDebut()
        );
    }

    // RG03 ===============================================================================

    @Test
    @DisplayName("Test date debut egual a date fin")
    void RG03_dateDebutEgualAFin(){
        Etudiant etudiant = dataRepo.getEtudiants().getFirst();
        Produit produit = dataRepo.getProduits().getFirst();
        final LocalDate DATE_DEBUT = LocalDate.now().plusDays(10);
        final LocalDate DATE_FIN = DATE_DEBUT;

        Assertions.assertDoesNotThrow(
                () -> reservationService.reserverProduit(etudiant.getId(), produit.getId(), DATE_DEBUT, DATE_FIN)
        );
    }

    @Test
    @DisplayName("Test date debut posterieur a date fin")
    void RG03_dateDebutSuperieurAFin(){
        Etudiant etudiant = dataRepo.getEtudiants().getFirst();
        Produit produit = dataRepo.getProduits().getFirst();
        final LocalDate DATE_DEBUT = LocalDate.now().plusDays(10);
        final LocalDate DATE_FIN = DATE_DEBUT.minusDays(1);

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.reserverProduit(etudiant.getId(), produit.getId(), DATE_DEBUT, DATE_FIN)
        );
    }
    // RG04 ===============================================================================

    @Test
    @DisplayName("Test calcule duree (date debut = date fin)")
    void RG04_calculeDureeMemeDate(){
        Etudiant etudiant = dataRepo.getEtudiants().getFirst();
        Produit produit = dataRepo.getProduits().getFirst();
        final LocalDate DATE_DEBUT = LocalDate.now().plusDays(10);
        final LocalDate DATE_FIN = DATE_DEBUT;

        Commande cmd = reservationService.reserverProduit(etudiant.getId(), produit.getId(), DATE_DEBUT, DATE_FIN);
        Assertions.assertEquals(1, cmd.getDuree());
    }

    @Test
    @DisplayName("Test calcule duree (2 jours)")
    void RG04_calculeDuree2Jour(){
        Etudiant etudiant = dataRepo.getEtudiants().getFirst();
        Produit produit = dataRepo.getProduits().getFirst();
        final LocalDate DATE_DEBUT = LocalDate.now().plusDays(10);
        final LocalDate DATE_FIN = DATE_DEBUT.plusDays(1);

        Commande cmd = reservationService.reserverProduit(etudiant.getId(), produit.getId(), DATE_DEBUT, DATE_FIN);
        Assertions.assertEquals(2, cmd.getDuree());
    }
    // RG05 ===============================================================================


    @Test
    @DisplayName("Test Etudiant qui n'exist pas")
    void RG05_etudiantNonExistant(){
        final int FAUX_ID_ETU = 100;
        Produit produit = dataRepo.getProduits().getFirst();
        final LocalDate DATE_DEBUT = LocalDate.now().plusDays(10);
        final LocalDate DATE_FIN = DATE_DEBUT.plusDays(1);

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.reserverProduit(FAUX_ID_ETU, produit.getId(), DATE_DEBUT, DATE_FIN)
        );
    }

    @Test
    @DisplayName("Test Produit qui n'exist pas")
    void RG05_produitNonExistant(){
        Etudiant etudiant = dataRepo.getEtudiants().getFirst();
        final int FAUX_ID_PROD = 100;
        final LocalDate DATE_DEBUT = LocalDate.now().plusDays(10);
        final LocalDate DATE_FIN = DATE_DEBUT.plusDays(1);

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.reserverProduit(etudiant.getId(), FAUX_ID_PROD, DATE_DEBUT, DATE_FIN)
        );
    }
    // RG06 ===============================================================================


    @Test
    @DisplayName("RG06 : Test duree max 7 jour (tester 7 jour)")
    void RG06_dureeMax7jourTest7jour(){

        Etudiant etudiant = dataRepo.getEtudiants().getFirst();
        Produit produit = dataRepo.getProduits().getFirst();
        final LocalDate DATE_DEBUT = LocalDate.now().plusDays(10);
        final LocalDate DATE_FIN = DATE_DEBUT.plusDays(6);

        Commande cmd = reservationService.reserverProduit(etudiant.getId(), produit.getId(), DATE_DEBUT, DATE_FIN);
        Assertions.assertEquals(7, cmd.getDuree());
    }

    @Test
    @DisplayName("RG06 : Test duree max 7 jour (tester 8 jour)")
    void RG06_dureeMax7jourTest8Jour(){

        Etudiant etudiant = dataRepo.getEtudiants().getFirst();
        Produit produit = dataRepo.getProduits().getFirst();
        final LocalDate DATE_DEBUT = LocalDate.now().plusDays(10);
        final LocalDate DATE_FIN = DATE_DEBUT.plusDays(7);

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.reserverProduit(etudiant.getId(), produit.getId(), DATE_DEBUT, DATE_FIN)
        );
    }
    // RG07 ===============================================================================

    @Test
    @DisplayName("RG07 : etudiant avec 1 cmd ancienne et 1 cmd a venir peut reserver")
    void RG07_etudiantA1CmdAncien1CmdNouv() {
        Etudiant etudiant = dataRepo.getEtudiants().getFirst();
        Produit produit1 = dataRepo.getProduits().get(0);
        Produit produit2 = dataRepo.getProduits().get(1);
        Produit produit3 = dataRepo.getProduits().get(2);
        final LocalDate DATE_DEBUT = LocalDate.now().plusDays(1);
        final LocalDate DATE_FIN = DATE_DEBUT.plusDays(4);

        // old commande: ended in 2020, inserted directly (the service would refuse it by RG02)
        dataRepo.enregistrer(new Commande(
                LocalDate.of(2020, 1, 2), LocalDate.of(2020, 1, 3), etudiant, produit1));

        // 1 commande a venir, created through the service
        reservationService.reserverProduit(etudiant.getId(), produit2.getId(), DATE_DEBUT, DATE_FIN);

        // 2nd counted commande (the new one counts in the limit), must be accepted
        Assertions.assertDoesNotThrow(() ->
                reservationService.reserverProduit(etudiant.getId(), produit3.getId(), DATE_DEBUT, DATE_FIN));
    }

    @Test
    @DisplayName("RG07 : etudiant avec 2 cmd a venir, ne peut pas reserver")
    void RG07_etudiantA2CmdNouveau() {
        Etudiant etudiant = dataRepo.getEtudiants().getFirst();
        Produit produit1 = dataRepo.getProduits().get(0);
        Produit produit2 = dataRepo.getProduits().get(1);
        Produit produit3 = dataRepo.getProduits().get(2);
        final LocalDate DATE_DEBUT = LocalDate.now().plusDays(1);
        final LocalDate DATE_FIN = DATE_DEBUT.plusDays(4);

        // 2 commande a venir, created through the service
        reservationService.reserverProduit(etudiant.getId(), produit1.getId(), DATE_DEBUT, DATE_FIN);
        reservationService.reserverProduit(etudiant.getId(), produit2.getId(), DATE_DEBUT, DATE_FIN);

        // 3rd counted commande (the new one counts in the limit), must be refused
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.reserverProduit(etudiant.getId(), produit3.getId(), DATE_DEBUT, DATE_FIN));
    }
    // RG08 ===============================================================================

    @Test
    @DisplayName("RG08 : produit indisponible !")
    void RG08_produitIndisponible(){
        Etudiant etudiant = dataRepo.getEtudiants().getFirst();
        Produit produit = dataRepo.getProduits().getFirst();

        final LocalDate DATE_DEBUT = LocalDate.now().plusDays(3);
        final LocalDate DATE_FIN = DATE_DEBUT.plusDays(4); // 5 days reservation from 3 to 7

//        Commande cmd = new Commande(DATE_DEBUT,DATE_FIN, etudiant, produit);
        reservationService.reserverProduit(etudiant.getId(), produit.getId(), DATE_DEBUT, DATE_FIN);

        System.out.println("Interval de reservation se croise");
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.reserverProduit(etudiant.getId(), produit.getId(), DATE_DEBUT.plusDays(2), DATE_FIN.plusDays(2))
        );

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.reserverProduit(etudiant.getId(), produit.getId(), DATE_DEBUT.minusDays(2), DATE_FIN.minusDays(2))
        );

        System.out.println("Interval de reservation se croise au borne");
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.reserverProduit(etudiant.getId(), produit.getId(), DATE_FIN, DATE_FIN.plusDays(2))
        );

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.reserverProduit(etudiant.getId(), produit.getId(), DATE_DEBUT.minusDays(2), DATE_DEBUT)
        );

        System.out.println("Pas d'intersection");
        Assertions.assertDoesNotThrow(
                () -> reservationService.reserverProduit(etudiant.getId(), produit.getId(), DATE_DEBUT.plusDays(9), DATE_FIN.plusDays(9))
        );
    }

    // RG09 ===============================================================================
    @Test
    void RG09_produitTarifJournalierNegative(){
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new Produit(
                        "produit",
                        new BigDecimal(-1),
                        new BigDecimal(10)
                )
        );
    }
    @Test
    void RG09_produitTarifJournalierZero(){
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new Produit(
                        "produit",
                        new BigDecimal(0),
                        new BigDecimal(10)
                )
        );
    }
    @Test
    void RG09_produitTarifJournalierPositive(){
        Assertions.assertDoesNotThrow(
                () -> new Produit(
                        "produit",
                        new BigDecimal(1),
                        new BigDecimal(10)
                )
        );
    }
    @Test
    void RG09_produitCautionNegative() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new Produit(
                        "produit",
                        new BigDecimal(4),
                        new BigDecimal(-1)
                )
        );
    }
    @Test
    void RG09_produitCautionZero(){
        Assertions.assertDoesNotThrow(
                () -> new Produit(
                        "produit",
                        new BigDecimal(7),
                        new BigDecimal(0)
                )
        );
    }
    @Test
    void RG09_produitCautionPositive(){
        Assertions.assertDoesNotThrow(
                () -> new Produit(
                        "produit",
                        new BigDecimal(7),
                        new BigDecimal(1)
                )
        );
    }

    // RG10 ===============================================================================


    @Test
    void RG10_calculeMontantLocation(){
        Etudiant etudiant = dataRepo.getEtudiants().getFirst();
        Produit produit = dataRepo.getProduits().getFirst();

        final int NOMBRE_JOUR = 3;
        final LocalDate DATE_DEBUT = LocalDate.now().plusDays(10);
        final LocalDate DATE_FIN = DATE_DEBUT.plusDays(NOMBRE_JOUR -1);

        Commande cmd = reservationService.reserverProduit(etudiant.getId(), produit.getId(), DATE_DEBUT, DATE_FIN);
        Assertions.assertEquals(
                produit.getTarifJournalier().multiply(
                        new BigDecimal(NOMBRE_JOUR)
                ).setScale(2, RoundingMode.HALF_UP) ,
                cmd.getMontantLocation()
        );
    }

    // RG11 ===============================================================================
    @Test
    void RG11_calculeMontantTotal(){
        Etudiant etudiant = dataRepo.getEtudiants().getFirst();
        Produit produit = dataRepo.getProduits().getFirst();

        final int NOMBRE_JOUR = 3;
        final LocalDate DATE_DEBUT = LocalDate.now().plusDays(10);
        final LocalDate DATE_FIN = DATE_DEBUT.plusDays(NOMBRE_JOUR -1);

        Commande cmd = reservationService.reserverProduit(etudiant.getId(), produit.getId(), DATE_DEBUT, DATE_FIN);
        Assertions.assertEquals(
                produit
                        .getTarifJournalier()
                        .multiply( new BigDecimal(NOMBRE_JOUR) )
                        .setScale(2, RoundingMode.HALF_UP )
                        .add(
                                produit.getCaution().setScale(2, RoundingMode.HALF_UP)
                        ) ,
                cmd.getMontantTotal()
        );
    }

    // RG12 ===============================================================================
    @Test
    @DisplayName("RG12 : montant location arrondi HALF_UP a 2 decimales")
    void RG12_arrondiHalfUp() {
        Produit produit = dataRepo.getProduits().getFirst();
        Etudiant etudiant = dataRepo.getEtudiants().getFirst();

        final LocalDate DATE = LocalDate.now().plusDays(3);

        Commande cmd = reservationService.reserverProduit(
                etudiant.getId(),
                produit.getId(),
                DATE, DATE
        );
        BigDecimal montantLocation = produit
                        .getTarifJournalier()
                        .setScale(2, RoundingMode.HALF_UP);

        Assertions.assertEquals(
                montantLocation,
                cmd.getMontantLocation()
        );
        Assertions.assertEquals(
                montantLocation
                        .add(
                                produit.getCaution().setScale(2, RoundingMode.HALF_UP)
                        ),
                cmd.getMontantTotal()
        );
    }
    // RG13 ===============================================================================

    @Test
    @DisplayName("RG13 : chaque commande a une reference unique")
    void RG13_referencesUniques() {
        Produit produit1 =  dataRepo.getProduits().get(1);
        Produit produit2 =  dataRepo.getProduits().get(2);
        Etudiant etudiant = dataRepo.getEtudiants().getFirst();

        final LocalDate DATE = LocalDate.now().plusDays(3);

        Commande cmd1 = reservationService.reserverProduit(
                etudiant.getId(),
                produit1.getId(),
                DATE, DATE
        );
        Commande cmd2 = reservationService.reserverProduit(
                etudiant.getId(),
                produit2.getId(),
                DATE, DATE
        );

        Assertions.assertNotEquals(
                cmd1.getReference(),
                cmd2.getReference()
        );
    }

    // RG14 ===============================================================================

    @Test
    @DisplayName("RG14 : une erreur de validation n'enregistre aucune commande")
    void RG14_erreurLaisseListeInchangee() {
        Produit produit1 =  dataRepo.getProduits().get(1);
        Produit produit2 =  dataRepo.getProduits().get(2);
        Etudiant etudiant = dataRepo.getEtudiants().getFirst();

        final LocalDate DATE = LocalDate.now().plusDays(3);

        reservationService.reserverProduit(
                etudiant.getId(),
                produit1.getId(),
                DATE, DATE
        );          // 1 valid commande
        int avant = dataRepo.commandesDeEtudiant(
                etudiant.getId()
        ).size();

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> reservationService.reserverProduit(
                        etudiant.getId(),
                        produit2.getId(),
                        DATE, DATE.minusDays(1))
        );  // RG03 refusal

        Assertions.assertEquals(
                avant, dataRepo.commandesDeEtudiant(etudiant.getId()).size()
        );
        Assertions.assertTrue(
                dataRepo.commandesDeProduit(produit2.getId()).isEmpty()
        );
    }

    // extra pour 100 coverage ===============================================================================

    @Test
    void produit100Coverage(){
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new Produit("prod", null, null)
        );
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new Produit("prod", new BigDecimal(5), null)
        );
    }

    @Test
    void RG08_periodeAvantCommandeExistante() {
        Etudiant etudiant = dataRepo.getEtudiants().getFirst();
        Produit produit = dataRepo.getProduits().getFirst();

        final LocalDate DEBUT_EXISTANTE = LocalDate.now().plusDays(5);
        final LocalDate FIN_EXISTANTE = DEBUT_EXISTANTE.plusDays(2);      // existing: days 5 to 7

        reservationService.reserverProduit(etudiant.getId(), produit.getId(), DEBUT_EXISTANTE, FIN_EXISTANTE);

        final LocalDate DEBUT_NOUVELLE = LocalDate.now();
        final LocalDate FIN_NOUVELLE = DEBUT_NOUVELLE.plusDays(2);        // new: days 0 to 2, ends before day 5

        Assertions.assertDoesNotThrow(
                () -> reservationService.reserverProduit(etudiant.getId(), produit.getId(), DEBUT_NOUVELLE, FIN_NOUVELLE)
        );
    }
}
