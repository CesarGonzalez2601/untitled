package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.model.Patient;
import org.example.model.PatientDAO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TreeViewController {

    @FXML
    private Canvas treeCanvas;

    private PatientDAO patientDAO = new PatientDAO();

    @FXML
    public void initialize() {
        List<Patient> patients = patientDAO.getAllPatients();
        drawDynamicTree(patients);
    }

    private void drawDynamicTree(List<Patient> patients) {
        GraphicsContext gc = treeCanvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);

        // Nodo raíz "pacientes"
        double rootX = 400;
        double rootY = 50;
        gc.strokeText("pacientes", rootX, rootY);

        // Agrupamos los pacientes por género
        Map<String, List<Patient>> byGender = patients.stream()
                .collect(Collectors.groupingBy(Patient::getGender));

        double offsetX = 200;
        double offsetY = 60;

        // Posiciones de nodos de género
        double maleX = rootX - offsetX;
        double femaleX = rootX + offsetX;
        double genderY = rootY + offsetY;

        // Dibujamos los nodos de género
        if (byGender.containsKey("hombre")) {
            gc.strokeLine(rootX, rootY + 10, maleX, genderY);
            gc.strokeText("hombre", maleX - 20, genderY + 10);
            drawBloodTypeTree(gc, byGender.get("hombre"), maleX, genderY + offsetY);
        }

        if (byGender.containsKey("mujer")) {
            gc.strokeLine(rootX, rootY + 10, femaleX, genderY);
            gc.strokeText("mujer", femaleX - 20, genderY + 10);
            drawBloodTypeTree(gc, byGender.get("mujer"), femaleX, genderY + offsetY);
        }
    }

    private void drawBloodTypeTree(GraphicsContext gc, List<Patient> patients, double startX, double startY) {
        // Agrupamos por tipo de sangre
        Map<String, List<Patient>> byBloodType = patients.stream()
                .collect(Collectors.groupingBy(Patient::getBloodType));

        double offsetX = 50;
        double offsetY = 60;

        // Posiciones para los tipos de sangre
        double bloodTypeAX = startX - offsetX;
        double bloodTypeBX = startX + offsetX;
        double bloodTypeY = startY + offsetY;

        if (byBloodType.containsKey("A")) {
            gc.strokeLine(startX, startY + 10, bloodTypeAX, bloodTypeY);
            gc.strokeText("A", bloodTypeAX - 5, bloodTypeY + 10);
            drawPressureTree(gc, byBloodType.get("A"), bloodTypeAX, bloodTypeY + offsetY);
        }

        if (byBloodType.containsKey("B")) {
            gc.strokeLine(startX, startY + 10, bloodTypeBX, bloodTypeY);
            gc.strokeText("B", bloodTypeBX - 5, bloodTypeY + 10);
            drawPressureTree(gc, byBloodType.get("B"), bloodTypeBX, bloodTypeY + offsetY);
        }

        // Repite para los otros tipos de sangre (AB, O)
        if (byBloodType.containsKey("AB")) {
            double bloodTypeABX = startX - offsetX * 2;
            gc.strokeLine(startX, startY + 10, bloodTypeABX, bloodTypeY);
            gc.strokeText("AB", bloodTypeABX - 5, bloodTypeY + 10);
            drawPressureTree(gc, byBloodType.get("AB"), bloodTypeABX, bloodTypeY + offsetY);
        }

        if (byBloodType.containsKey("O")) {
            double bloodTypeOX = startX + offsetX * 2;
            gc.strokeLine(startX, startY + 10, bloodTypeOX, bloodTypeY);
            gc.strokeText("O", bloodTypeOX - 5, bloodTypeY + 10);
            drawPressureTree(gc, byBloodType.get("O"), bloodTypeOX, bloodTypeY + offsetY);
        }
    }

    private void drawPressureTree(GraphicsContext gc, List<Patient> patients, double startX, double startY) {
        // Agrupamos por presión arterial
        Map<String, List<Patient>> byPressure = patients.stream()
                .collect(Collectors.groupingBy(Patient::getPressure));

        double offsetX = 20;
        double offsetY = 60;

        double pressureHighX = startX - offsetX;
        double pressureLowX = startX + offsetX;
        double pressureY = startY + offsetY;

        if (byPressure.containsKey("alta")) {
            gc.strokeLine(startX, startY + 10, pressureHighX, pressureY);
            gc.strokeText("alta", pressureHighX - 10, pressureY + 10);
            drawPatients(gc, byPressure.get("alta"), pressureHighX, pressureY + offsetY);
        }

        if (byPressure.containsKey("media")) {
            double pressureMediumX = startX;
            gc.strokeLine(startX, startY + 10, pressureMediumX, pressureY);
            gc.strokeText("media", pressureMediumX - 10, pressureY + 10);
            drawPatients(gc, byPressure.get("media"), pressureMediumX, pressureY + offsetY);
        }

        if (byPressure.containsKey("baja")) {
            gc.strokeLine(startX, startY + 10, pressureLowX, pressureY);
            gc.strokeText("baja", pressureLowX - 10, pressureY + 10);
            drawPatients(gc, byPressure.get("baja"), pressureLowX, pressureY + offsetY);
        }
    }

    private void drawPatients(GraphicsContext gc, List<Patient> patients, double startX, double startY) {
        double offsetY = 20;
        for (int i = 0; i < patients.size(); i++) {
            gc.strokeText(patients.get(i).getName(), startX - 20, startY + (i * offsetY));
        }
    }
}
