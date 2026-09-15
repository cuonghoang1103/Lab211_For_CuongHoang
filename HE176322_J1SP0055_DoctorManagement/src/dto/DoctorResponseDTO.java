package dto;

import constants.Constants;

/**
 * DTO carrying one doctor FROM the controller OUT TO the view - a JavaBean (private
 * fields, public no-argument constructor, getters/setters).
 *
 * @author HE176322
 */
public class DoctorResponseDTO {

    // Code shown in the first column.
    private String code;
    // Name shown in the second column.
    private String name;
    // Specialization shown in the third column.
    private String specialization;
    // Availability shown in the last column.
    private int availability;

    // JavaBean constructor: an empty row, filled through the setters.
    public DoctorResponseDTO() {
    }

    // Creates the response with every column filled in.
    public DoctorResponseDTO(String code, String name, String specialization,
            int availability) {
        this.code = code;
        this.name = name;
        this.specialization = specialization;
        this.availability = availability;
    }

    // Returns the code.
    public String getCode() {
        return code;
    }

    // Sets the code.
    public void setCode(String code) {
        this.code = code;
    }

    // Returns the name.
    public String getName() {
        return name;
    }

    // Sets the name.
    public void setName(String name) {
        this.name = name;
    }

    // Returns the specialization.
    public String getSpecialization() {
        return specialization;
    }

    // Sets the specialization.
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    // Returns the availability.
    public int getAvailability() {
        return availability;
    }

    // Sets the availability.
    public void setAvailability(int availability) {
        this.availability = availability;
    }

    // One table row, already padded into fixed-width columns, so the view only has to
    // print it.
    @Override
    public String toString() {
        return String.format(Constants.ROW_FORMAT, code, name, specialization,
                availability);
    }
}
