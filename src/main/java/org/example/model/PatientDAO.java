package org.example.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    // Método para agregar un nuevo paciente a la base de datos
    public void addPatient(Patient patient) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO pacientes (nombre, genero, tipo_sangre, presion_arterial) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, patient.getName());
            ps.setString(2, patient.getGender());
            ps.setString(3, patient.getBloodType());
            ps.setString(4, patient.getPressure());

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
            ps.setString(1, patient.getGender());
            ps.setString(2, patient.getBloodType());
            ps.setString(3, patient.getPressure());
            ps.setInt(4, patient.getId());

            ps.executeUpdate(); // Ejecuta la actualización

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
