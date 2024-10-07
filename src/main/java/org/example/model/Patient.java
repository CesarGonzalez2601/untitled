package org.example.model;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    private int id;
    private String nombre;
    private String genero;
    private String tipoSangre;
    private String presionArterial;

    public Patient(String nombre,String genero, String tipoSangre, String presionArterial) {
        this.nombre = nombre;
        this.genero = genero;
        this.tipoSangre = tipoSangre;
        this.presionArterial = presionArterial;
    }
}