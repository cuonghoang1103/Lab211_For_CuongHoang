package dto;

/**
 * DTO carrying the answer of one menu option FROM the controller OUT TO the view - a
 * JavaBean with the one line to print.
 *
 * @author HE176322
 */
public class AccountResponseDTO {

    // The line to print: "Create account successfully!" or "Login successful!".
    private String message;

    // JavaBean constructor: an empty answer, filled through the setter.
    public AccountResponseDTO() {
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
