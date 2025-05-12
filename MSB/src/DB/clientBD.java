
package DB;

import Model.Client;
import Service.service;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class clientBD {

    public static boolean ajouterClient(Client Client) {
        String sql = "INSERT INTO client(nom, prenom, adresse, telephone, mot_de_passe) VALUES (?,?,?,?,?)";

        try (Connection con = new BDConnexion().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, Client.getNom());
            ps.setString(2, Client.getPrenom());
            ps.setString(3, Client.getAdresse());
            ps.setString(4, Client.getTelephone());
            ps.setString(5, Client.getmotDepasse());

            int lignes = ps.executeUpdate();

            if (lignes > 0) {
                int idclient = getidClientParTelephone(Client.getTelephone(), con);

                String numeroCompte = service.creerComptePourClient(idclient);

                if (numeroCompte == null) {
                    System.out.println("Le compte n'a pas été correctement initialisé.");
                    return false;
                }

                System.out.println("Client créé avec succès !");
                System.out.println("Numéro de compte attribué : " + numeroCompte);
                System.out.println("Votre solde initial est de 0.0000");
                System.out.println("Votre compte a été bien créé !");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du client : " + e.getMessage());
        }

        return false;
    }

    public static boolean mettreÀJourClient(Client client) {
        try (Connection con = new BDConnexion().getConnection()) {

            String sql = "UPDATE client SET nom = ?, prenom = ?, adresse = ?, telephone =?, mot_de_passe = ? WHERE id_client =?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, client.getNom());
            ps.setString(2, client.getPrenom());
            ps.setString(3, client.getAdresse());
            ps.setString(4, client.getTelephone());
            ps.setString(5, client.getmotDepasse());
            ps.setInt(6, client.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getidClientParTelephone(String telephone, Connection con) {
           int idClient = -1;
           try{
               String sql = "SELECT id_client FROM client WHERE telephone =?";
               PreparedStatement ps = con.prepareStatement(sql);
               ps.setString(1, telephone);
               ResultSet rs = ps.executeQuery();

               if (rs.next()) {
                   idClient = rs.getInt("id_client");
               }
               rs.close();
               ps.close();
           } catch (Exception e) {
               e.printStackTrace();
           }
           return idClient;
    }

    public Client getClientParConnexion(String telephone, String motDepasse) {
        Client cl = null;

        try {
            Connection con = new BDConnexion().getConnection();

            String sql = "SELECT * FROM client WHERE telephone = ? ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, telephone);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String motDepasseBD = rs.getString("mot_de_passe");
                if (motDepasse.equals(motDepasseBD)){
                    cl = new Client(
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("adresse"),
                            rs.getString("telephone"),
                            rs.getString("mot_de_passe")
                    );

                }

            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cl;
    }
    public boolean telephoneExiste(String telephone){
        try {
            Connection con = new BDConnexion().getConnection();
            String sql = "SELECT * FROM client WHERE telephone = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, telephone);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public static boolean changerMotDePasse(String telephone, String ancienMdp, String nouveauMdp){

        try (Connection con = new BDConnexion().getConnection())
        {
            String checkSql = "SELECT mot_de_passe FROM client WHERE telephone =?";
            PreparedStatement checkStmt = con.prepareStatement(checkSql);
            checkStmt.setString(1, telephone);
            ResultSet rs = checkStmt.executeQuery();

               if (rs.next()){
                   String motDePasseActuel = rs.getString("mot_de_passe");
                   if (!motDePasseActuel.equals(ancienMdp)){
                       System.out.println("Erreur : ancien mot de passe incorrect :");
                       return false;
                   }

                   String updateSql = "UPDATE client SET mot_de_passe =? WHERE telephone =?";
                   PreparedStatement updateStmt = con.prepareStatement(updateSql);
                   updateStmt.setString(1, nouveauMdp);
                   updateStmt.setString(2, telephone);
                   int Lignes = updateStmt.executeUpdate();

                   if (Lignes > 0) {
                      // System.out.println("Mot de passe modifé avec succès !");
                             return true;
                   }
               }

        } catch (Exception e) {
            System.out.println("Erreur lors du changement de mot de passe : "+e.getMessage());
        }
        return false;
    }

    public static List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        try (Connection con = new BDConnexion().getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM client")) {

            while (rs.next()) {
                Client c = new Client();
                c.setId(rs.getInt("id_client"));
                c.SetNom(rs.getString("nom"));
                c.SetPrenom(rs.getString("prenom"));
                c.SetAdresse(rs.getString("adresse"));
                c.SetTelephone(rs.getString("telephone"));
                c.SetmotDepasse(rs.getString("mot_de_passe"));
                clients.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }
    public static int getTotalClients() {
        try (Connection con = new BDConnexion().getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM client")) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void updateClient(Client client) {
        try (Connection conn = new BDConnexion().getConnection()) {
            String sql = "UPDATE client SET nom=?, prenom=?, adresse=?, telephone=?, mot_de_passe=? WHERE id_client=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, client.getNom());
            ps.setString(2, client.getPrenom());
            ps.setString(3, client.getAdresse());
            ps.setString(4, client.getTelephone());
            ps.setString(5, client.getmotDepasse());
            ps.setInt(6, client.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}