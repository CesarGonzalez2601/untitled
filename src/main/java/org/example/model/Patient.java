package org.example.model;

public class Patient {

    private int id;
    private String name;
    private String gender;
    private String bloodType;
    private String pressure;

    // Constructor con id
    public Patient(int id, String name, String gender, String bloodType, String pressure) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.bloodType = bloodType;
        this.pressure = pressure;
    }

    @Override
    public String toString() {
        return this.name;  // Devuelve el nombre del paciente como su representación en el ComboBox
    }
    // Constructor sin id
    public Patient(String name, String gender, String bloodType, String pressure) {
        this.name = name;
        this.gender = gender;
        this.bloodType = bloodType;
        this.pressure = pressure;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getPressure() {
        return pressure;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }
}
