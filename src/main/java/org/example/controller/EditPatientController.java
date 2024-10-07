package org.example.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import org.example.model.Patient;
import org.example.model.PatientDAO;

import java.util.List;

public class EditPatientController {

    @FXML
    private ComboBox<Patient> patientComboBox;

    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    private ComboBox<String> bloodTypeComboBox;

    @FXML
    private ComboBox<String> bloodPressureComboBox;

    @FXML
    private TextField nameField;

    @FXML
    private Button updateButton;

    private PatientDAO patientDAO = new PatientDAO();

    @FXML
    public void initialize() {
        // Cargar la lista de pacientes en el ComboBox
        List<Patient> patients = patientDAO.getAllPatients();
        patientComboBox.setItems(FXCollections.observableArrayList(patients));

        // Inicializar opciones de los ComboBox de género, tipo de sangre y presión arterial
        genderComboBox.setItems(FXCollections.observableArrayList("Masculino", "Femenino", "Otro"));
        bloodTypeComboBox.setItems(FXCollections.observableArrayList("A", "B", "AB", "O"));
        bloodPressureComboBox.setItems(FXCollections.observableArrayList("Alta", "Normal", "Baja"));

        // Manejar la selección del paciente y llenar los campos correspondientes
        patientComboBox.setOnAction(event -> {
            Patient selectedPatient = patientComboBox.getSelectionModel().getSelectedItem();
            if (selectedPatient != null) {
                nameField.setText(selectedPatient.getName());
                genderComboBox.setValue(selectedPatient.getGender());
                bloodTypeComboBox.setValue(selectedPatient.getBloodType());
                bloodPressureComboBox.setValue(selectedPatient.getPressure());
            }
        });
    }

    @FXML
    private void handleUpdatePatient() {
        // Obtener el paciente seleccionado
        Patient selectedPatient = patientComboBox.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            // Actualizar los datos del paciente con los valores de los campos
            selectedPatient.setName(nameField.getText());
            selectedPatient.setGender(genderComboBox.getValue());
            selectedPatient.setBloodType(bloodTypeComboBox.getValue());
            selectedPatient.setPressure(bloodPressureComboBox.getValue());

            // Actualizar el paciente en la base de datos
            patientDAO.updatePatient(selectedPatient);

            // Mostrar un mensaje de confirmación o refrescar la interfaz, si es necesario
            System.out.println("Paciente actualizado correctamente.");
        }
    }
}

