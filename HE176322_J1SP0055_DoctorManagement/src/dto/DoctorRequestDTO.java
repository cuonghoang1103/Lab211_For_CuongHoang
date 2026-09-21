package dto;

/**
 * DTO carrying what the user typed, FROM main INTO the controller.
 *
 * @author HE176322
 */
public class DoctorRequestDTO {

    // Code typed by the user; identifies the doctor to add/update/delete.
    private String code;

    // Name typed by the user; empty on update means "keep the old name".
    private String name;

    // Specialization typed by the user; empty on update means "keep".
    private String specialization;

    // Availability typed by the user.
    private Integer availability;

    // Text to look for in code, name or specialization (search only).
    private String searchText;

    // Creates an empty request; main fills it through the setters.
    public DoctorRequestDTO() {
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
    public Integer getAvailability() {
        return availability;
    }

    // Sets the availability.
    public void setAvailability(Integer availability) {
        this.availability = availability;
    }

    // Returns the search text.
    public String getSearchText() {
        return searchText;
    }

    // Sets the search text.
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
