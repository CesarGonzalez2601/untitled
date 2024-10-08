package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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

            mostrarMensaje("Paciente actualizado con éxito.");

            recargarDatosPaciente(selectedPatient);
        }
    }

    private void recargarDatosPaciente(Patient paciente) {
        nameField.setText(paciente.getNombre());
        genderComboBox.setValue(paciente.getGenero());
        bloodTypeComboBox.setValue(paciente.getTipoSangre());
        bloodPressureComboBox.setValue(paciente.getPresionArterial());
    }

    @FXML
    private void handleGoBack() {

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

    @FXML
    public void handleClassifyPatient() {
        Patient selectedPatient = patientComboBox.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {

            Stage stage = (Stage) patientComboBox.getScene().getWindow();
            stage.close();

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PacienteCView.fxml"));
                Parent root = loader.load();


                PacienteCController pacienteCController = loader.getController();
                pacienteCController.setPatient(selectedPatient);


                Stage mainStage = new Stage();
                mainStage.setTitle("Clasificación de Pacientes");


                mainStage.initStyle(StageStyle.UNDECORATED);

                mainStage.setScene(new Scene(root, 1200, 800));

                mainStage.centerOnScreen();

                mainStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No se ha seleccionado ningún paciente.");
        }
    }

    @FXML
    private void handleDeletePatient() {
        Patient selectedPatient = patientComboBox.getValue();

        if (selectedPatient != null) {
            PatientDAO pacienteDAO = new PatientDAO();
            boolean deleted = pacienteDAO.eliminarPaciente(selectedPatient.getId());

            if (deleted) {

                mostrarMensaje("El paciente se ha eliminado con éxito.");
                handleGoBack();
            } else {
                System.out.println("Error al eliminar el paciente.");
            }
        } else {
            System.out.println("No se ha seleccionado ningún paciente para eliminar.");
        }
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
