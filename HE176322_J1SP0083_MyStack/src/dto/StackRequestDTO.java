package dto;

/**
 * DTO carrying what the user typed FROM main INTO the controller: the value to push.
 *
 * @author HE176322
 */
public class StackRequestDTO {

    // The value to push, already checked to be a whole number.
    private int value;

    // JavaBean constructor: an empty request; main fills it with the setter.
    public StackRequestDTO() {
    }

    // Returns the value to push.
    public int getValue() {
        return value;
    }

    // Sets the value to push.
    public void setValue(int value) {
        this.value = value;
    }
}
