package sv.com.pruebanodospacientes.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sv.com.pruebanodospacientes.conexion.ConexionBD;

public class PacientesDAO {

    public List<Paciente> obtenerTodosLosPacientes() {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT nombre, genero, tipo_sangre, presion_arterial FROM pacientes";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Paciente paciente = new Paciente(
                        rs.getString("nombre"),
                        rs.getString("genero"),
                        rs.getString("tipo_sangre"),
                        rs.getString("presion_arterial")
                );
                pacientes.add(paciente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pacientes;
    }
}
