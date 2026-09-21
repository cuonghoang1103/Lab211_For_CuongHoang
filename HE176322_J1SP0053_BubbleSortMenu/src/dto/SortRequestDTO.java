package dto;

/**
 * DTO carrying the elements typed by the user FROM main INTO the controller.
 *
 * @author HE176322
 */
public class SortRequestDTO {

    // The elements in the order they were typed.
    private int[] elementArray;

    // Creates an empty request; main fills it through the setter.
    public SortRequestDTO() {
    }

    // Returns the elements.
    public int[] getElementArray() {
        return elementArray;
    }

    // Sets the elements.
    public void setElementArray(int[] elementArray) {
        this.elementArray = elementArray;
    }
}
