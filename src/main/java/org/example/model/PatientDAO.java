package org.example.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

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

    public List<Patient> obtenerTodosLosPacientes() {
        List<Patient> pacientes = new ArrayList<>();
        String sql = "SELECT id, nombre, genero, tipo_sangre, presion_arterial FROM pacientes"; // Incluye el ID

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Patient paciente = new Patient();
                paciente.setId(rs.getInt("id"));
                paciente.setNombre(rs.getString("nombre"));
                paciente.setGenero(rs.getString("genero"));
                paciente.setTipoSangre(rs.getString("tipo_sangre"));
                paciente.setPresionArterial(rs.getString("presion_arterial"));
                pacientes.add(paciente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pacientes;
    }

    public void updatePatient(Patient patient) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE pacientes SET nombre = ?, genero = ?, tipo_sangre = ?, presion_arterial = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, patient.getNombre());
            ps.setString(2, patient.getGenero());
            ps.setString(3, patient.getTipoSangre());
            ps.setString(4, patient.getPresionArterial());
            ps.setInt(5, patient.getId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Patient obtenerPacientePorId(int id) {
        Patient paciente = null;
        String sql = "SELECT id, nombre, genero, tipo_sangre, presion_arterial FROM pacientes WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    paciente = new Patient();
                    paciente.setId(resultSet.getInt("id"));
                    paciente.setNombre(resultSet.getString("nombre"));
                    paciente.setGenero(resultSet.getString("genero"));
                    paciente.setTipoSangre(resultSet.getString("tipo_sangre"));
                    paciente.setPresionArterial(resultSet.getString("presion_arterial"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paciente;
    }

    public boolean eliminarPaciente(int id) {
        String sql = "DELETE FROM pacientes WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
