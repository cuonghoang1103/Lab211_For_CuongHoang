package dto;

/**
 * DTO carrying what the user typed FROM main INTO the controller: the size of the array
 * to generate.
 *
 * @author HE176322
 */
public class SortRequestDTO {

    // How many random numbers to generate.
    private int size;

    // Creates an empty request; main fills it through the setter.
    public SortRequestDTO() {
    }

    // Returns the size.
    public int getSize() {
        return size;
    }

    // Sets the size.
    public void setSize(int size) {
        this.size = size;
    }
}
