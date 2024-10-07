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

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para recuperar todos los pacientes (devuelve todos los datos del paciente)
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

    // Método para recuperar todos los pacientes (devuelve solo nombre, género, tipo de sangre y presión arterial)
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

    // Método para actualizar los datos de un paciente existente
    public void updatePatient(Patient patient) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE pacientes SET nombre = ?, genero = ?, tipo_sangre = ?, presion_arterial = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, patient.getNombre());
            ps.setString(2, patient.getGenero());
            ps.setString(3, patient.getTipoSangre());
            ps.setString(4, patient.getPresionArterial());
            ps.setInt(5, patient.getId());

            ps.executeUpdate(); // Ejecuta la actualización

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
