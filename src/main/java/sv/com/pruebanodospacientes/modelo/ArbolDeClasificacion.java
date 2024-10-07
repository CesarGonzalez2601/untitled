package sv.com.pruebanodospacientes.modelo;

import java.util.ArrayList;
import java.util.List;

public class ArbolDeClasificacion {
    private Nodo raiz;

    public ArbolDeClasificacion() {
        raiz = new Nodo("pacientes");
    }

    public void insertarPaciente(Paciente paciente) {
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

        actual.setPaciente(paciente);
    }

    // Método para obtener la lista de todos los pacientes
    public List<Paciente> getPacientes() {
        List<Paciente> pacientes = new ArrayList<>();
        obtenerPacientesRecursivo(raiz, pacientes);
        return pacientes;
    }

    // Método recursivo para recorrer el árbol y recolectar pacientes
    private void obtenerPacientesRecursivo(Nodo nodo, List<Paciente> pacientes) {
        if (nodo == null) return;

        if (nodo.esHoja() && nodo.getPaciente() != null) {
            pacientes.add(nodo.getPaciente());
        } else {
            for (Nodo hijo : nodo.getHijos().values()) {
                obtenerPacientesRecursivo(hijo, pacientes);
            }
        }
    }

    // Método para contar pacientes por categoría
    public int contarPacientesPorCategoria(String categoria) {
        return contarPacientesRecursivo(raiz, categoria);
    }

    // Método recursivo para contar los pacientes en una categoría específica
    private int contarPacientesRecursivo(Nodo nodo, String categoria) {
        int contador = 0;

        if (nodo == null) return contador;

        // Si es una hoja y coincide con la categoría, incrementar el contador
        if (nodo.esHoja() && nodo.getPaciente() != null) {
            Paciente paciente = nodo.getPaciente();
            if (categoria.equals(paciente.getGenero()) ||
                    categoria.equals(paciente.getTipoSangre()) ||
                    categoria.equals(paciente.getPresionArterial())) {
                contador++;
            }
        } else {
            // Recorrer los hijos recursivamente
            for (Nodo hijo : nodo.getHijos().values()) {
                contador += contarPacientesRecursivo(hijo, categoria);
            }
        }

        return contador;
    }

    public Nodo getRaiz() {
        return raiz;
    }
}