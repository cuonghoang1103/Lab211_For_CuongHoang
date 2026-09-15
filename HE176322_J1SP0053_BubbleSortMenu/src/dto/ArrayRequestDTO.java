package dto;

/**
 * DTO carrying the elements typed by the user FROM main INTO the controller.
 *
 * @author HE176322
 */
public class ArrayRequestDTO {

    // The elements in the order they were typed.
    private int[] elements;

    // Creates an empty request; main fills it through the setter.
    public ArrayRequestDTO() {
    }

    // Returns the elements.
    public int[] getElements() {
        return elements;
    }

    // Sets the elements.
    public void setElements(int[] elements) {
        this.elements = elements;
    }
}
