package dto;

/**
 * DTO carrying a sorted array FROM the controller OUT TO the view.
 *
 * @author HE176322
 */
public class SortResponseDTO {

    // The elements, already in the order to display.
    private int[] values;

    // JavaBean constructor: an empty response, filled through the setter.
    public SortResponseDTO() {
    }

    // Creates the response with the sorted elements.
    public SortResponseDTO(int[] values) {
        this.values = values;
    }

    // Returns the sorted elements.
    public int[] getValues() {
        return values;
    }

    // Sets the sorted elements.
    public void setValues(int[] values) {
        this.values = values;
    }
}
