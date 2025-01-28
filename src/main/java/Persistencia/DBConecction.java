package Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConecction {
    
    /* PRUEBA SERVIDOR LOCAL
    private static final String URL = "jdbc:mysql://localhost:3306/bd_tienda";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    */
    
    /* PRUEBA FREESQLDATABASE  */
    private static final String URL = "jdbc:mysql://sql10.freesqldatabase.com:3306/sql10759697";
    private static final String USER = "sql10759697";
    private static final String PASSWORD = "wr3eVEwcNE";
    

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
