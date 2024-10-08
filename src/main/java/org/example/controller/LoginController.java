package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.example.model.User;
import org.example.model.UserDAO;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private ImageView logoImageView;

    @FXML
    private GridPane gridPane;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    private void handleLogin() {
        String usuario = usernameField.getText();
        String contraseña = passwordField.getText();

        User user = userDAO.authenticate(usuario, contraseña);

        if (user != null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Login Exitoso");
            alert.setHeaderText(null);
            alert.setContentText("¡Bienvenido, " + user.getUsuario() + "!");
            alert.showAndWait();

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main_view.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.close();

                Stage mainStage = new Stage();
                mainStage.setTitle("Panel Principal");
                mainStage.setScene(new Scene(root, 1200, 700));
                mainStage.initStyle(StageStyle.UNDECORATED);
                mainStage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error en el Login");
            alert.setHeaderText(null);
            alert.setContentText("Usuario o contraseña incorrectos.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleExitApp() {
        Stage stage = (Stage) gridPane.getScene().getWindow();
        stage.close();
    }
}
