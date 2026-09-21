package dto;

/**
 * DTO carrying the answer of a login FROM the controller OUT TO the view: the one line to
 * print, already in the chosen language.
 *
 * @author HE176322
 */
public class LoginResponseDTO {

    // The line to print: the success line, or the error of a check that failed.
    private String message;

    // Creates an empty answer; the service fills it through the setter.
    public LoginResponseDTO() {
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
