package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.model.Patient;
import org.example.model.PatientDAO;

import java.io.IOException;
import java.util.List;

public class EditPatientController {

    @FXML
    private ComboBox<Patient> patientComboBox;
    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<String> genderComboBox;
    @FXML
    private ComboBox<String> bloodTypeComboBox;
    @FXML
    private ComboBox<String> bloodPressureComboBox;

    private ObservableList<Patient> patients;

    @FXML
    public void initialize() {
        PatientDAO patientDAO = new PatientDAO();
        List<Patient> patientList = patientDAO.getAllPatients();
        patients = FXCollections.observableArrayList(patientList);

        patientComboBox.setItems(patients);

        // Personalizar cómo se muestran los pacientes en el ComboBox
        patientComboBox.setCellFactory(param -> new ListCell<Patient>() {
            @Override
            protected void updateItem(Patient item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText("ID: " + item.getId() + " - " + item.getNombre());
                }
            }
        });

        patientComboBox.setOnAction(event -> {
            Patient selectedPatient = patientComboBox.getSelectionModel().getSelectedItem();
            if (selectedPatient != null) {
                nameField.setText(selectedPatient.getNombre());
                genderComboBox.setValue(selectedPatient.getGenero());
                bloodTypeComboBox.setValue(selectedPatient.getTipoSangre());
                bloodPressureComboBox.setValue(selectedPatient.getPresionArterial());
            }
        });
    }

    @FXML
    public void handleUpdatePatient() {
        Patient selectedPatient = patientComboBox.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            selectedPatient.setNombre(nameField.getText());
            selectedPatient.setGenero(genderComboBox.getValue());
            selectedPatient.setTipoSangre(bloodTypeComboBox.getValue());
            selectedPatient.setPresionArterial(bloodPressureComboBox.getValue());

            PatientDAO patientDAO = new PatientDAO();
            patientDAO.updatePatient(selectedPatient);
            System.out.println("Paciente actualizado con éxito.");
        }
    }

    @FXML
    public void handleGoBack() {
        Stage stage = (Stage) patientComboBox.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main_view.fxml"));
            Parent root = loader.load();

            Stage mainStage = new Stage();
            mainStage.setTitle("Main");
            mainStage.setScene(new Scene(root, 800, 600));
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleClassifyPatient() {
        Patient selectedPatient = patientComboBox.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            // Cierra la ventana actual
            Stage stage = (Stage) patientComboBox.getScene().getWindow();
            stage.close();

            try {
                // Carga el FXML para PacienteCController
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PacienteCView.fxml"));
                Parent root = loader.load();

                // Obtén el controlador y establece el paciente
                PacienteCController pacienteCController = loader.getController();
                pacienteCController.setPatient(selectedPatient);

                // Crea y muestra la nueva ventana
                Stage mainStage = new Stage();
                mainStage.setTitle("Clasificación de Pacientes");
                mainStage.setScene(new Scene(root, 800, 600)); // Establece el tamaño de la ventana aquí
                mainStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No se ha seleccionado ningún paciente.");
        }
    }
}