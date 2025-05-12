package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDConnexion {
    private static final String URL = "jdbc:mysql://localhost:3306/mini_systeme_bancaire";
    private static final String User ="root";
    private static final String PASSWORD ="";
    private Connection con;

    public BDConnexion() throws SQLException{
     con = DriverManager.getConnection(URL,User,PASSWORD);
    }
    public Connection getConnection() throws SQLException{
        return con;
   }
}