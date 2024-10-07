package org.example.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    // Método para agregar un nuevo paciente a la base de datos
    public void addPatient(Patient patient) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO pacientes (nombre, genero, tipo_sangre, presion_arterial) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, patient.getNombre());
            ps.setString(2, patient.getGenero());
            ps.setString(3, patient.getTipoSangre());
            ps.setString(4, patient.getPresionArterial());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para recuperar todos los pacientes
    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT id, nombre, genero, tipo_sangre, presion_arterial FROM pacientes";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Patient patient = new Patient(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("genero"),
                        rs.getString("tipo_sangre"),
                        rs.getString("presion_arterial")
                );
                patients.add(patient);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return patients;
    }

    public void updatePatient(Patient patient) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE pacientes SET genero = ?, tipo_sangre = ?, presion_arterial = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, patient.getGenero());
            ps.setString(2, patient.getTipoSangre());
            ps.setString(3, patient.getPresionArterial());
            ps.setInt(4, patient.getId());

            ps.executeUpdate(); // Ejecuta la actualización

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Patient> obtenerTodosLosPacientes() {
        List<Patient> pacientes = new ArrayList<>();
        String sql = "SELECT nombre, genero, tipo_sangre, presion_arterial FROM pacientes";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Patient paciente = new Patient(
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
