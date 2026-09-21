package dto;

/**
 * DTO carrying one result FROM the controller OUT TO the view: the one line to print,
 * already holding the value the operation worked on and the stack after it.
 *
 * @author HE176322
 */
public class StackResponseDTO {

    // The line to print, e.g. "Popped 30.   Stack (top -> bottom): [20, 10]".
    private String message;

    // JavaBean constructor: an empty response, filled through the setter.
    public StackResponseDTO() {
    }

    // Returns the line to print.
    public String getMessage() {
        return message;
    }

    // Sets the line to print.
    public void setMessage(String message) {
        this.message = message;
    }
}
