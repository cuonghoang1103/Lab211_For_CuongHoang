package dto;

/**
 * DTO carrying one country FROM the controller OUT TO the view - a JavaBean.
 *
 * @author HE176322
 */
public class CountryResponseDTO {

    // The full table row of one country.
    private String information;

    // JavaBean constructor: an empty row, filled through the setter.
    public CountryResponseDTO() {
    }

    // Creates the row with its text.
    public CountryResponseDTO(String information) {
        this.information = information;
    }

    // Returns the row text.
    public String getInformation() {
        return information;
    }

    // Sets the row text.
    public void setInformation(String information) {
        this.information = information;
    }
}
