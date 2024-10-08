package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class MainController {

    @FXML
    private Button addPatientButton;

    @FXML
    private void handleAddPatient(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/add_patient.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Agregar Paciente");

            stage.initStyle(StageStyle.UNDECORATED);
            stage.setWidth(1200);
            stage.setHeight(700);

            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        closeWindow(event);
    }

    @FXML
    private void handleViewTree(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/tree_view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Diagrama de Clasificación de Pacientes");

            stage.initStyle(StageStyle.UNDECORATED);
            stage.setWidth(1200);
            stage.setHeight(700);

            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        closeWindow(event);
    }

    @FXML
    private void handleEditPatients(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/edit_patient.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Editar Paciente");

            stage.initStyle(StageStyle.UNDECORATED);
            stage.setWidth(1200);
            stage.setHeight(700);

            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        closeWindow(event);
    }

    @FXML
    private void handleViewPaciente(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PacienteView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Clasificación Pacientes");

            stage.initStyle(StageStyle.UNDECORATED);
            stage.setWidth(1200);
            stage.setHeight(700);

            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        closeWindow(event);
    }

    @FXML
    private void handleBackToLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            Parent loginView = loader.load();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(loginView));

            currentStage.setWidth(1200);
            currentStage.setHeight(700);

            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
