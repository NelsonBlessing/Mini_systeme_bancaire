package Service;

import java.security.SecureRandom;
import Modelcompte.Compte;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import DB.BDConnexion;
import DB.compteBD;
import Utlis.Format;
import DB.transactionBD;
import Utlis.Session;

public class service {

    public static boolean deposer(String telephone, double montant, boolean afficherDetails) {
            if (montant <= 0){
                if (afficherDetails){
                    System.out.println("Montant invalide. vous devez entrer un montant stritement positif !");
                }
                return false;
            }

        try {
            Connection con = new BDConnexion().getConnection();

               if (afficherDetails){
                 //  System.out.println("Tententive de recherche du compte lié au numéro :" + telephone);
                }

            String sqlSelect = "SELECT compte.id_compte, solde FROM compte INNER JOIN client ON compte.id_client = client.id_client WHERE client.telephone = ?";
            PreparedStatement psSelect = con.prepareStatement(sqlSelect);
            psSelect.setString(1, telephone);
            ResultSet rs = psSelect.executeQuery();

            if (rs.next()) {
                int idcompte = rs.getInt("id_compte");
                double soldeActuel = rs.getDouble("solde");
                double nouveauSolde = soldeActuel + montant;

              if (afficherDetails && montant > 0){
                  System.out.println("ID Compte trouvé :" + idcompte);
                  System.out.println("Ancien solde :" + soldeActuel+ ", Nouveau solde :" +nouveauSolde);

              }
                // Mise à jour du solde
                String sqlUpdate = "UPDATE compte SET solde = ? WHERE id_compte = ?";
                PreparedStatement psUpdate = con.prepareStatement(sqlUpdate);
                psUpdate.setDouble(1, nouveauSolde);
                psUpdate.setInt(2, idcompte);
                psUpdate.executeUpdate();

                // Insertion dans la table transaction
                String sqlInsert = "INSERT INTO transaction (id_compte, type_operation, montant, date_operation) VALUES (?, ?, ?, ?)";
                PreparedStatement psInsert = con.prepareStatement(sqlInsert);
                psInsert.setInt(1, idcompte);
                psInsert.setString(2, "depot");
                psInsert.setDouble(3, montant);
                psInsert.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
                psInsert.executeUpdate();

                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean retirer(String telephone, double montant){
        if (montant <= 0){
            System.out.println("Le montant doit etre supérieur à zéro");
            return false;
        }
        try {
            Connection con = new BDConnexion().getConnection();
            String sqlcompte = "SELECT compte.id_compte,compte.solde FROM compte INNER JOIN client ON compte.id_client = client.id_client WHERE client.id_client =?;";
            PreparedStatement psCompte = con.prepareStatement(sqlcompte);
            psCompte.setString(1, telephone);
            ResultSet rs = psCompte.executeQuery();

            if (rs.next()){

                int idcompte = rs.getInt("id_compte");
                double soldeActuel = rs.getDouble("solde");



                double soldeMinimum = 999;

                if (montant > soldeActuel - soldeMinimum){
                    System.out.println("Vous devez laisser au moins "+soldeMinimum +"FBU sur le compte.");
                    return false;
                }

                //Mis à jour du solde
                double nouveauSolde = soldeActuel - montant;
                String sqlMaj = "UPDATE compte SET solde = ? WHERE id_compte =?";
                PreparedStatement psMaj = con.prepareStatement(sqlMaj);
                psMaj.setDouble(1, nouveauSolde);
                psMaj.setInt(2, idcompte);
                psMaj.executeUpdate();

                // Enregistrement de la transaction
                String sqlTrans = "INSERT INTO transaction (id_compte, type_operation, montant, date_operation) VALUES (?,?,?, NOW())";
                PreparedStatement psTrans = con.prepareStatement(sqlTrans);
                psTrans.setInt(1,idcompte);
                psTrans.setString(2,"retrait");
                psTrans.setDouble(3, montant);
                psTrans.executeUpdate();

                   return true;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    public static String creerComptePourClient(int idclient){
        try {
            Connection con = new BDConnexion().getConnection();
            String getLastIdSQL = "SELECT MAX(id_compte) AS dernier_id FROM compte";
            PreparedStatement psLastId = con.prepareStatement(getLastIdSQL);
            ResultSet rs = psLastId.executeQuery();


            int dernierId = 0;
            if (rs.next()){
                dernierId = rs.getInt("dernier_id") + 1;
            }

            String numeroCompte = "COMPTE" + (200010 + dernierId + 1);

            String sql ="INSERT INTO compte(id_client, numero_compte, solde) VALUES (?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,idclient);
            ps.setString(2,numeroCompte);
            ps.setDouble(3,0.00);

               int lignes = ps.executeUpdate();
               if (lignes > 0){
                   return numeroCompte;
               }
        } catch (Exception e){
            e.printStackTrace();

        }
        return null;
    }

    public boolean transfert(int idClientSource, String telephoneDestinataire, double montant) {
        Connection con = null;
        try {
            con = new BDConnexion().getConnection();
            con.setAutoCommit(false); // Démarrage de la transaction

            // Étape 1 : Récupérer le compte source
            Compte compteSource = compteBD.getCompteParIdClient(idClientSource);
            if (compteSource == null) {
                System.out.println("Compte source introuvable !");
                return false;
            }

            // Étape 2 : Récupérer le compte destinataire via téléphone
            telephoneDestinataire = Format.formatTelephone(telephoneDestinataire);
             compteBD cbd = new compteBD();
            Compte compteDestinataire = cbd.getCompteParTelephoneAvecNom(telephoneDestinataire, con);
            if (compteDestinataire == null) {
                System.out.println("Compte destinataire introuvable pour le numero : "+ telephoneDestinataire);
                return false;
            }

            // Étape 3 : Empêcher de se transférer à soi-même
            if (compteSource.getIdClient() == compteDestinataire.getIdClient()) {
                System.out.println("Vous ne pouvez pas vous transférer de l'argent à vous-même !");
                return false;
            }

            // Étape 4 : Vérifier le solde
            if (compteSource.getSolde() - montant < 999) {
                System.out.println("Solde insuffisant !");
                return false;
            }

            if (montant <= 0) {
                System.out.println("Montant invalide !");
                return false;
            }


            // Étape 6 : Mise à jour des soldes
            compteBD.metterAJourSolde(compteSource.getIdCompte(), compteSource.getSolde() - montant, con);
            compteBD.metterAJourSolde(compteDestinataire.getIdCompte(), compteDestinataire.getSolde() + montant, con);

            // Étape 7 : Historique de transaction
            transactionBD.ajouterTransaction(compteSource.getIdCompte(), "TransfertSortant", montant, con);
            transactionBD.ajouterTransaction(compteDestinataire.getIdCompte(), "TransfertEntrant", montant, con);

            con.commit();
            return true;

        } catch (Exception e) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println("Erreur lors du transfert : " + e.getMessage());
            return false;
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public String getNomDestinataire(String telephone, Connection con) {
        compteBD cbd = new compteBD();
        Compte compte = cbd.getCompteParTelephoneAvecNom(telephone, con);
        if (compte != null) {
            return compte.getPrenomClient() + " " + compte.getNomClient();
        }
        return null;
    }

}
       
