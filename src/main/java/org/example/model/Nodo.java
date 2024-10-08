package org.example.model;

import java.util.HashMap;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Nodo {
    private String criterio;
    private HashMap<String, Nodo> hijos;
    private List<Patient> pacientes;  // Lista de pacientes en lugar de un solo paciente

    public Nodo(String criterio) {
        this.criterio = criterio;
        this.hijos = new HashMap<>();
        this.pacientes = new ArrayList<>();
    }

    public void agregarHijo(String clave, Nodo nodo) {
        hijos.put(clave, nodo);
    }

    public Nodo obtenerHijo(String clave) {
        return hijos.get(clave);
    }

    // Añadir un paciente a la lista de pacientes
    public void agregarPaciente(Patient patient) {
        this.pacientes.add(patient);
    }

    public List<Patient> getPacientes() {
        return pacientes;
    }

    public boolean esHoja() {
        return hijos.isEmpty();
    }

    public String getCriterio() {
        return criterio;
    }

    public HashMap<String, Nodo> getHijos() {
        return hijos;
    }
}
