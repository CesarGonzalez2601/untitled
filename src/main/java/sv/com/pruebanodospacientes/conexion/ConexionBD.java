package sv.com.pruebanodospacientes.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:postgresql://localhost:5432/pacientes_db";
    private static final String USER = "postgres";  // Reemplaza con tu usuario de PostgreSQL
    private static final String PASSWORD = "pass";  // Reemplaza con tu contraseña de PostgreSQL

    public static Connection conectar() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Conectado exitosa");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Error al cargar el driver de PostgreSQL", e);
        }
    }
}