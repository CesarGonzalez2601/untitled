package sv.com.pruebanodospacientes.modelo;

import java.util.HashMap;

public class Nodo {
    private String criterio;  // Este campo debe existir
    private HashMap<String, Nodo> hijos;
    private Paciente paciente;

    public Nodo(String criterio) {
        this.criterio = criterio;
        this.hijos = new HashMap<>();
    }

    public void agregarHijo(String clave, Nodo nodo) {
        hijos.put(clave, nodo);
    }

    public Nodo obtenerHijo(String clave) {
        return hijos.get(clave);
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public boolean esHoja() {
        return hijos.isEmpty();
    }

    // Añadir el método getCriterio() para resolver el error
    public String getCriterio() {
        return criterio;
    }

    public HashMap<String, Nodo> getHijos() {
        return hijos;
    }
}