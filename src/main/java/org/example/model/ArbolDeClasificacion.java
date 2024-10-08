package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class ArbolDeClasificacion {
    private Nodo raiz;

    public ArbolDeClasificacion() {
        raiz = new Nodo("Pacientes");
    }

    public void insertarPaciente(Patient paciente) {
        Nodo actual = raiz;

        if (actual.obtenerHijo(paciente.getGenero()) == null) {
            actual.agregarHijo(paciente.getGenero(), new Nodo(paciente.getGenero()));
        }
        actual = actual.obtenerHijo(paciente.getGenero());

        if (actual.obtenerHijo(paciente.getTipoSangre()) == null) {
            actual.agregarHijo(paciente.getTipoSangre(), new Nodo(paciente.getTipoSangre()));
        }
        actual = actual.obtenerHijo(paciente.getTipoSangre());

        if (actual.obtenerHijo(paciente.getPresionArterial()) == null) {
            actual.agregarHijo(paciente.getPresionArterial(), new Nodo(paciente.getPresionArterial()));
        }
        actual = actual.obtenerHijo(paciente.getPresionArterial());

        actual.agregarPaciente(paciente);
    }

    public List<Patient> getPacientes() {
        List<Patient> pacientes = new ArrayList<>();
        obtenerPacientesRecursivo(raiz, pacientes);
        return pacientes;
    }

    private void obtenerPacientesRecursivo(Nodo nodo, List<Patient> pacientes) {
        if (nodo == null) return;

        if (!nodo.getPacientes().isEmpty()) {
            pacientes.addAll(nodo.getPacientes());
        }

        for (Nodo hijo : nodo.getHijos().values()) {
            obtenerPacientesRecursivo(hijo, pacientes);
        }
    }

    public Nodo getRaiz() {
        return raiz;
    }
}