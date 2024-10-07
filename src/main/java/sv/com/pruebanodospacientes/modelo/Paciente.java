package sv.com.pruebanodospacientes.modelo;


public class Paciente {
    private String nombre;
    private String genero;
    private String tipoSangre;
    private String presionArterial;
    private String identificador;  // Identificador único (Paciente 1, Paciente 2...)

    public Paciente(String nombre,String genero, String tipoSangre, String presionArterial) {
        this.nombre = nombre;
        this.genero = genero;
        this.tipoSangre = tipoSangre;
        this.presionArterial = presionArterial;
    }


    // Getters y setters
    public String getGenero() {
        return genero;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public String getPresionArterial() {
        return presionArterial;
    }

    public String getNombre() {
        return nombre;
    }
}