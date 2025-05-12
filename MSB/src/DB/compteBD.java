package DB;

import Modelcompte.Compte;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class compteBD{
    public static boolean ajouterCompte(int id_client){
        String sql = "INSERT INTO compte(id_client, solde) VALUES (?,?)";

        try (Connection con = new BDConnexion().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id_client);
                ps.setDouble(2,0.000);

                int lignes = ps.executeUpdate();
                return lignes > 0;
            } catch (Exception e){
                System.out.println("Erreur lors de l'ajout du compte :"+ e.getMessage());
                return false;
            }
    }
    public static int getIdCompteParTelephone(String telephone){
        int id = -1;
        try (Connection con = new BDConnexion().getConnection()){
            String sql = "SELECT compte.id_compte FROM compte INNER JOIN client ON compte.id_client = client.id_client WHERE client.telephone = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, telephone);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                id = rs.getInt("id_compte");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }
    public static double getSoldeParTelephone(String telephone){
        double solde = -1;
        try {
            Connection con = new BDConnexion().getConnection();
            String sql = "SELECT compte.solde FROM compte INNER JOIN client ON compte.id_client = client.id_client WHERE client.telephone = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,telephone);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                solde = rs.getDouble("solde");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return solde;
    }
    public static double getSoldeParClient(int idClient) {
        double solde = 0.00;
        try {
            Connection con = new BDConnexion().getConnection();
            String sql = "SELECT solde FROM compte WHERE id_client = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idClient);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                solde = rs.getDouble("solde");
            } else {
                System.out.println("Aucun compte trouvé pour ce client.");
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération du solde !");
            e.printStackTrace();
        }
        return solde;
    }
    public static Compte getCompteParIdClient(int idClient){
        Compte compte = null;

        try {
            Connection con = new BDConnexion().getConnection();
            String sql = "SELECT * FROM compte WHERE id_client =?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idClient);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                compte = new Compte(rs.getInt("id_compte"),
                        rs.getString("numero_compte"),
                        rs.getDouble("solde"),
                        rs.getString("date_creation"),
                        rs.getInt("id_client")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return compte;
    }

    public Compte getCompteParTelephoneAvecNom(String telephone, Connection con) {
        Compte compte = null;
        try {
            String sql = "SELECT c.id_compte, c.numero_compte, c.solde, c.date_creation, c.id_client, " +
                    "cl.nom, cl.prenom " +
                    "FROM compte c JOIN client cl ON c.id_client = cl.id_client " +
                    "WHERE cl.telephone = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, telephone);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                compte = new Compte();
                compte.setIdCompte(rs.getInt("id_compte"));
                compte.setNumeroCompte(rs.getString("numero_compte"));
                compte.setSolde(rs.getDouble("solde"));
                compte.setDateCreation(rs.getString("date_creation"));
                compte.setIdClient(rs.getInt("id_client"));
                compte.setNomClient(rs.getString("nom"));
                compte.setPrenomClient(rs.getString("prenom"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return compte;
    }
    public static boolean metterAJourSolde(int idCompte, double nouveauSolde, Connection con){
        try {
            String sql = "UPDATE compte SET solde = ? WHERE id_compte = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, nouveauSolde);
            ps.setInt(2, idCompte);
            int lignes = ps.executeUpdate();
            return lignes > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getTotalComptes() {
        try (Connection con = new BDConnexion().getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM compte")) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static List<Compte> getAllComptes() {
        List<Compte> comptes = new ArrayList<>();
        try (Connection con = new BDConnexion().getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM compte")) {

            while (rs.next()) {
                Compte cp = new Compte();
                cp.setIdCompte(rs.getInt("id_compte"));
                cp.setNumeroCompte(rs.getString("numero_compte"));
                cp.setSolde(rs.getDouble("solde"));
                cp.setDateCreation(rs.getString("date_creation"));
                cp.setIdClient(rs.getInt("id_client"));
                comptes.add(cp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comptes;
    }
}

