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

        // Personalizar cómo se muestran los pacientes
        patientComboBox.setCellFactory(new Callback<ListView<Patient>, ListCell<Patient>>() {
            @Override
            public ListCell<Patient> call(ListView<Patient> param) {
                return new ListCell<Patient>() {
                    @Override
                    protected void updateItem(Patient item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText("ID: " + item.getId() + " - " + item.getNombre());
                        }
                    }
                };
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
        }
    }

    @FXML
    public void handleGoBack() throws IOException {

        Stage stage = (Stage) patientComboBox.getScene().getWindow();
        stage.close();  // Cierra la ventana actual

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main_view.fxml"));
            Parent root = loader.load();

            Stage mainStage = new Stage();
            mainStage.setTitle("Main");
            mainStage.setScene(new Scene(root, 800, 600)); // Establece el tamaño de la ventana aquí
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
