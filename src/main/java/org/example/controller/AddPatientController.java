package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.model.Patient;
import org.example.model.PatientDAO;

import java.io.IOException;

public class AddPatientController {

    @FXML
    private TextField nameField;

    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    private ComboBox<String> bloodTypeComboBox;

    @FXML
    private ComboBox<String> pressureComboBox;

    private final PatientDAO patientDAO = new PatientDAO();

    @FXML
    private void handleAddPatient() {

        String name = nameField.getText();
        String gender = genderComboBox.getValue();
        String bloodType = bloodTypeComboBox.getValue();
        String pressure = pressureComboBox.getValue();


        if (name.isEmpty() || gender == null || bloodType == null || pressure == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, completa todos los campos.");
            alert.showAndWait();
        } else {

            Patient patient = new Patient(name, gender, bloodType, pressure);
            try {
                patientDAO.addPatient(patient);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Paciente Agregado");
                alert.setHeaderText(null);
                alert.setContentText("Paciente agregado exitosamente.");
                alert.showAndWait();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al agregar el paciente");
                alert.setHeaderText(null);
                alert.setContentText("Hubo un error al agregar el paciente: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void handleBack() {

        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main_view.fxml"));
            Parent root = loader.load();

            Stage mainStage = new Stage();
            mainStage.setTitle("Main");
            mainStage.setScene(new Scene(root, 1200, 700));
            mainStage.initStyle(StageStyle.UNDECORATED);
            mainStage.centerOnScreen();
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


