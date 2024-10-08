package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.model.Patient;
import org.example.model.PatientDAO;
import org.example.model.ArbolDeClasificacion;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PacienteController {

    @FXML
    private Canvas canvas;

    @FXML
    private TextField nameField;

    private ArbolDeClasificacion arbol;


    public void initialize() {
        arbol = new ArbolDeClasificacion();
        inicializarPacientesDesdeBD();
        dibujarArbol();
    }

    private void inicializarPacientesDesdeBD() {
        PatientDAO pacienteDAO = new PatientDAO();
        for (Patient paciente : pacienteDAO.obtenerTodosLosPacientes()) {
            arbol.insertarPaciente(paciente);
        }
    }


    private void dibujarArbol() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        double rootX = canvas.getWidth() / 2;
        double rootY = 50;
        double offsetX = canvas.getWidth() / 6;
        double offsetY = 120;

        dibujarNodo(gc, "Pacientes", rootX, rootY, 14, true);

        gc.strokeLine(rootX, rootY + 20, rootX - 2 * offsetX, rootY + offsetY - 20); // Línea a "Género"
        gc.strokeLine(rootX, rootY + 20, rootX, rootY + offsetY - 20);               // Línea a "Grupo Sanguíneo"
        gc.strokeLine(rootX, rootY + 20, rootX + 2 * offsetX, rootY + offsetY - 20); // Línea a "Presión Arterial"

        dibujarSubrama(gc, "Género", rootX - 2 * offsetX, rootY + offsetY, offsetX / 2, "genero");
        dibujarSubrama(gc, "Grupo Sanguíneo", rootX, rootY + offsetY, offsetX / 2, "sangre");
        dibujarSubrama(gc, "Presión Arterial", rootX + 2 * offsetX, rootY + offsetY, offsetX / 2, "presion");
    }

    private void dibujarSubrama(GraphicsContext gc, String titulo, double x, double y, double offsetX, String categoria) {
        dibujarNodo(gc, titulo, x, y, 12, true);

        double childY = y + 70;
        double childX = x;

        if (categoria.equals("genero")) {
            dibujarListaPacientes(gc, "Masculino", childX - offsetX, childY, "M", x, y);
            dibujarListaPacientes(gc, "Femenino", childX + offsetX, childY, "F", x, y);
        } else if (categoria.equals("sangre")) {
            dibujarListaPacientes(gc, "A", childX - 1.5 * offsetX, childY, "A", x, y);
            dibujarListaPacientes(gc, "B", childX - 0.5 * offsetX, childY, "B", x, y);
            dibujarListaPacientes(gc, "AB", childX + 0.5 * offsetX, childY, "AB", x, y);
            dibujarListaPacientes(gc, "O", childX + 1.5 * offsetX, childY, "O", x, y);
        } else if (categoria.equals("presion")) {
            dibujarListaPacientes(gc, "Alta", childX - offsetX, childY, "alta", x, y);
            dibujarListaPacientes(gc, "Media", childX, childY, "media", x, y);
            dibujarListaPacientes(gc, "Baja", childX + offsetX, childY, "baja", x, y);
        }
    }

    private void dibujarNodo(GraphicsContext gc, String texto, double x, double y, int fontSize, boolean centrado) {
        Text text = new Text(texto);
        text.setFont(new Font("Arial", fontSize));
        double textWidth = text.getLayoutBounds().getWidth();
        double textHeight = text.getLayoutBounds().getHeight();
        double nodeWidth = Math.max(80, textWidth + 20);
        double nodeHeight = textHeight + 20;

        gc.setStroke(Color.DARKBLUE);
        gc.setFill(Color.LIGHTYELLOW);

        // Dibujar rectángulo para nodos
        gc.fillRect(x - nodeWidth / 2, y - nodeHeight / 2, nodeWidth, nodeHeight);
        gc.strokeRect(x - nodeWidth / 2, y - nodeHeight / 2, nodeWidth, nodeHeight);

        // Dibujar texto centrado
        gc.setFill(Color.BLACK);
        gc.fillText(texto, x - textWidth / 2, y + textHeight / 4);
    }

    private void dibujarListaPacientes(GraphicsContext gc, String categoria, double x, double y, String tipo, double parentX, double parentY) {

        gc.strokeLine(parentX, parentY + 25, x, y - 25);


        dibujarNodo(gc, categoria, x, y, 12, true);


        List<Patient> pacientesPorCategoria = obtenerPacientesPorCategoria(tipo);


        if (!pacientesPorCategoria.isEmpty()) {
            double listaY = y + 50;
            double rectWidth = calcularAnchoMaximoTexto(gc, pacientesPorCategoria) + 20;
            double rectHeight = 20 * pacientesPorCategoria.size();

            double listaX = x - rectWidth / 2;

            gc.setStroke(Color.DARKBLUE);
            gc.setFill(Color.WHITE);
            gc.fillRect(listaX, listaY, rectWidth, rectHeight);
            gc.strokeRect(listaX, listaY, rectWidth, rectHeight);

            gc.strokeLine(x, y + 25, listaX + rectWidth / 2, listaY - 10);

            double pacienteY = listaY + 15;
            for (Patient paciente : pacientesPorCategoria) {
                gc.setFill(Color.BLACK);
                gc.fillText(paciente.getNombre(), listaX + 10, pacienteY);
                pacienteY += 20;
            }
        }
    }

    private double calcularAnchoMaximoTexto(GraphicsContext gc, List<Patient> pacientes) {
        double maxWidth = 0;
        for (Patient paciente : pacientes) {
            Text text = new Text(paciente.getNombre());
            text.setFont(new Font("Arial", 12));
            double textWidth = text.getLayoutBounds().getWidth();
            if (textWidth > maxWidth) {
                maxWidth = textWidth;
            }
        }
        return maxWidth;
    }


    private List<Patient> obtenerPacientesPorCategoria(String tipo) {
        List<Patient> pacientesFiltrados = new ArrayList<>();
        for (Patient paciente : arbol.getPacientes()) {

            if ((tipo.equals("M") && paciente.getGenero().equals("Hombre")) ||
                    (tipo.equals("F") && paciente.getGenero().equals("Mujer")) ||
                    (tipo.equals("A") && paciente.getTipoSangre().equals("A")) ||
                    (tipo.equals("B") && paciente.getTipoSangre().equals("B")) ||
                    (tipo.equals("AB") && paciente.getTipoSangre().equals("AB")) ||
                    (tipo.equals("O") && paciente.getTipoSangre().equals("O")) ||
                    (tipo.equals("alta") && paciente.getPresionArterial().equals("Alta")) ||
                    (tipo.equals("media") && paciente.getPresionArterial().equals("Media")) ||
                    (tipo.equals("baja") && paciente.getPresionArterial().equals("Baja"))) {

                pacientesFiltrados.add(paciente);
                System.out.println("Paciente agregado a la categoría " + tipo + ": " + paciente.getNombre());
            }
        }
        System.out.println("Total pacientes en la categoría " + tipo + ": " + pacientesFiltrados.size());
        return pacientesFiltrados;
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