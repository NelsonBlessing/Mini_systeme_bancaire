package MDPutil;

import DB.BDConnexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Utlis.Session.idClient;

public class motDpasse {
    public static boolean verifierMotDePasse(String saisie, String motEnregistrer){
        return saisie.equals(motEnregistrer);
    }
    public static String recupererMotDePasseDepuisBD(int idClient) {
        String motDePasse = null;
        try (Connection con = new BDConnexion().getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT mot_de_passe FROM client WHERE id_client =?")){
            ps.setInt(1, idClient);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                motDePasse = rs.getString("mot_de_passe");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return motDePasse;
    }

    public static void mettreAjourPIN(int idClient, String nouveauPIN){
        try (Connection con = new BDConnexion().getConnection();
        PreparedStatement ps = con.prepareStatement("UPDATE client SET mot_de_passe = ? WHERE id_client =?")) {
            ps.setString(1, nouveauPIN);
            ps.setInt(2, idClient);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean supprimerCompte(int idClient) {
        try (Connection con = new BDConnexion().getConnection()){

            PreparedStatement ps1 = con.prepareStatement("DELETE FROM transaction WHERE id_compte IN (SELECT id_compte FROM compte WHERE id_client =?)");
            ps1.setInt(1, idClient);
            ps1.executeUpdate();

            PreparedStatement ps2 = con.prepareStatement("DELETE FROM compte WHERE id_client = ?");
            ps2.setInt(1, idClient);
            ps2.executeUpdate();

            PreparedStatement ps3 = con.prepareStatement("DELETE FROM client WHERE id_client =?");
            ps3.setInt(1, idClient);
            ps3.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
