package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
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

public class PacienteCController {

    @FXML
    private Label recommendationLabel;
    @FXML
    private Canvas canvas;

    private ArbolDeClasificacion arbol;
    private Patient selectedPatient;
    private int idPatient;

    public void initialize() {
        arbol = new ArbolDeClasificacion();
        dibujarArbol();
    }

    public void setPatient(Patient patient) {
        if (patient != null) {
            this.selectedPatient = patient;
            System.out.println("Paciente seleccionado: " + patient.getNombre());
            inicializarPacientesDesdeBD(patient.getId());

            generarRecomendacion(patient);
        } else {
            System.out.println("Paciente es null.");
        }
    }

    private void inicializarPacientesDesdeBD(int idPatient) {
        PatientDAO pacienteDAO = new PatientDAO();
        Patient paciente = pacienteDAO.obtenerPacientePorId(idPatient);
        System.out.println(idPatient + " Este es el error de prueba de id");
        if (paciente != null) {
            arbol.insertarPaciente(paciente);
        } else {
            System.out.println("Paciente no encontrado en la base de datos.");
        }
        dibujarArbol();
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

        gc.fillRect(x - nodeWidth / 2, y - nodeHeight / 2, nodeWidth, nodeHeight);
        gc.strokeRect(x - nodeWidth / 2, y - nodeHeight / 2, nodeWidth, nodeHeight);

        gc.setFill(Color.BLACK);
        gc.fillText(texto, x - textWidth / 2, y + textHeight / 4);
    }

    private void dibujarListaPacientes(GraphicsContext gc, String categoria, double x, double y, String tipo, double parentX, double parentY) {
        gc.strokeLine(parentX, parentY + 25, x, y - 25);

        dibujarNodo(gc, categoria, x, y, 12, true);

        List<Patient> pacientesPorCategoria = obtenerPacientesPorCategoria(tipo);

        if (!pacientesPorCategoria.isEmpty()) {
            double listaY = y + 50;
            double rectWidth = calcularAnchoMaximoTexto(gc, pacientesPorCategoria) + 20; // 20 es un padding adicional
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
            text.setFont(new Font("Arial", 12));  // Usa la misma fuente y tamaño que se usa al dibujar el texto
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
            }
        }
        return pacientesFiltrados;
    }

    private void generarRecomendacion(Patient patient) {
        StringBuilder recomendaciones = new StringBuilder("Recomendaciones para el paciente:\n");

        if (patient.getGenero().equals("Mujer")) {
            recomendaciones.append("- Las mujeres en El Salvador tienen una mayor prevalencia de hipertensión, especialmente después de los 50 años. Es recomendable realizar chequeos regulares.\n");
        } else if (patient.getGenero().equals("Hombre")) {
            recomendaciones.append("- Los hombres en El Salvador tienen un mayor riesgo de desarrollar enfermedades cardiovasculares. Se sugiere monitoreo frecuente de la presión arterial.\n");
        }

        switch (patient.getTipoSangre()) {
            case "A":
                recomendaciones.append("- El tipo de sangre A es común en El Salvador. Se recomienda mantener una dieta balanceada y hacer ejercicio regular. Evita el consumo excesivo de carne roja.\n");
                recomendaciones.append("Estadística: Aproximadamente el 34% de la población salvadoreña tiene el tipo de sangre A.\n");
                break;
            case "B":
                recomendaciones.append("- El tipo de sangre B está asociado con algunos problemas digestivos. Considera adoptar una dieta rica en vegetales y carne magra.\n");
                recomendaciones.append("Estadística: Solo el 10% de la población tiene el tipo de sangre B en El Salvador.\n");
                break;
            case "AB":
                recomendaciones.append("- El tipo de sangre AB es raro en El Salvador. Es importante tener identificados posibles donantes de sangre debido a la rareza de este tipo.\n");
                recomendaciones.append("Estadística: Menos del 5% de la población en El Salvador tiene tipo de sangre AB.\n");
                break;
            case "O":
                recomendaciones.append("- El tipo de sangre O es el más común y el donante universal. Es importante mantener un estilo de vida saludable para prevenir riesgos de hipertensión o problemas cardíacos.\n");
                recomendaciones.append("Estadística: El 50% de la población salvadoreña tiene tipo de sangre O.\n");
                break;
        }

        if (patient.getPresionArterial().equals("Alta")) {
            recomendaciones.append("- Tu presión arterial está alta. En El Salvador, la hipertensión es una de las principales causas de hospitalización. Es recomendable reducir el consumo de sal y hacer ejercicio regularmente.\n");
            recomendaciones.append("Estadística: El 30% de los adultos mayores en El Salvador sufre de hipertensión.\n");
        } else if (patient.getPresionArterial().equals("Media")) {
            recomendaciones.append("- Tu presión arterial está dentro de lo normal. En El Salvador, se recomienda mantener hábitos saludables y realizar chequeos periódicos para mantener la presión en niveles normales.\n");
            recomendaciones.append("Estadística: Aproximadamente el 60% de la población tiene presión arterial normal.\n");
        } else if (patient.getPresionArterial().equals("Baja")) {
            recomendaciones.append("- Tu presión arterial está baja. Es importante mantenerse hidratado y consumir alimentos con sal moderada. En El Salvador, los casos de hipotensión no son comunes, pero deben ser monitoreados.\n");
            recomendaciones.append("Estadística: Solo un 5% de los casos registrados en El Salvador corresponden a presión arterial baja.\n");
        }

        recommendationLabel.setText(recomendaciones.toString());
    }



    @FXML
    private void handleBack() {
        Stage stage = (Stage) canvas.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/edit_patient.fxml"));
            Parent root = loader.load();
            Stage mainStage = new Stage();
            mainStage.setTitle("Main");

            mainStage.initStyle(StageStyle.UNDECORATED);

            mainStage.setScene(new Scene(root, 1200, 800));

            mainStage.centerOnScreen();

            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
