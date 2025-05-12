package DB;

import Modeltransaction.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class transactionBD {

    public static List<Transaction>getTransactionParTelephone(String telephone) {
        List<Transaction> liste = new ArrayList<>();

        try {
            Connection con = new BDConnexion().getConnection();
            String sqlcompte = "SELECT compte.id_compte FROM compte INNER JOIN client ON compte.id_client = client.id_client WHERE client.telephone =?";
            PreparedStatement psCompte = con.prepareStatement(sqlcompte);
            psCompte.setString(1,telephone);
            ResultSet rsCompte = psCompte.executeQuery();

            if (rsCompte.next()){
                int idCompte = rsCompte.getInt("id_compte");

                String sql ="SELECT * FROM transaction WHERE id_compte =? ORDER BY date_operation DESC";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1,idCompte);
                ResultSet rs = ps.executeQuery();

                while (rs.next()){
                    Transaction t = new Transaction();
                    t.setId(rs.getInt("id_transaction"));
                    t.setIdcompte(rs.getInt("id_compte"));
                    t.setType(rs.getString("type_operation"));
                    t.setMontant(rs.getDouble("montant"));
                    t.setDate(new Date(rs.getTimestamp("date_operation").getTime()));
                    liste.add(t);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return liste;

    }
    public static boolean ajouterTransaction(int idCompte, String typeOperation, double montant, Connection con){

        try {
            String sql = "INSERT INTO transaction(id_compte, type_operation, montant, date_operation) VALUES (?,?,?, NOW())";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idCompte);
            ps.setString(2, typeOperation);
            ps.setDouble(3, montant);
            int Lignes = ps.executeUpdate();

            return Lignes > 0;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        try (Connection con = new BDConnexion().getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM transaction ORDER BY date_operation DESC")) {

            while (rs.next()) {
                Transaction t = new Transaction();
                t.setId(rs.getInt("id_transaction"));
                t.setIdcompte(rs.getInt("id_compte"));
                t.setType(rs.getString("type_operation"));
                t.setMontant(rs.getDouble("montant"));
                t.setDate(rs.getDate("date_operation"));
                transactions.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public static int getTotalTransactions() {
        try (Connection con = new BDConnexion().getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM transaction")) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}