package dto;

/**
 * DTO carrying one result FROM the controller OUT TO the view: the value the operation
 * worked on and the stack after it.
 *
 * @author HE176322
 */
public class StackResponseDTO {

    // The value pushed, popped or peeked (unused by option 4, Display).
    private int value;
    // The stack after the operation, top first.
    private String stack;

    // JavaBean constructor: an empty response, filled through the setters.
    public StackResponseDTO() {
    }

    // Returns the value the operation worked on.
    public int getValue() {
        return value;
    }

    // Sets the value the operation worked on.
    public void setValue(int value) {
        this.value = value;
    }

    // Returns the stack as text.
    public String getStack() {
        return stack;
    }

    // Sets the stack as text.
    public void setStack(String stack) {
        this.stack = stack;
    }
}
