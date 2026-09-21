package model;

import constants.Constants;

/**
 * MODEL: describes one doctor, and nothing else.
 *
 * @author HE176322
 */
public class Doctor {

    // Unique code of the doctor.
    private String code;

    // Full name of the doctor.
    private String name;

    // Medical specialization.
    private String specialization;

    // Number of available shifts; the brief requires it to be >= 0.
    private int availability;

    // Creates an empty doctor, to be filled through the setters.
    public Doctor() {
    }

    // Creates a doctor with every field filled in.
    public Doctor(String code, String name, String specialization, int availability) {
        this.code = code;
        this.name = name;
        this.specialization = specialization;
        this.availability = availability;
    }

    // Returns the doctor code.
    public String getCode() {
        return code;
    }

    // Changes the doctor code.
    public void setCode(String code) {
        this.code = code;
    }

    // Returns the doctor name.
    public String getName() {
        return name;
    }

    // Changes the doctor name.
    public void setName(String name) {
        this.name = name;
    }

    // Returns the specialization.
    public String getSpecialization() {
        return specialization;
    }

    // Changes the specialization.
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    // Returns the availability.
    public int getAvailability() {
        return availability;
    }

    // Changes the availability.
    public void setAvailability(int availability) {
        this.availability = availability;
    }

    // Polymorphism: overrides Object.toString() - one table row, already padded into
    // fixed-width columns (Guide: "cần output gì thì thêm hàm toString()").
    @Override
    public String toString() {
        return String.format(Constants.ROW_FORMAT, code, name, specialization, availability);
    }
}
