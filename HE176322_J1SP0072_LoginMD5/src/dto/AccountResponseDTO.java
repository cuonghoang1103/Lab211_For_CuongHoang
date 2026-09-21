package dto;

/**
 * DTO carrying the answer of one flow FROM the controller OUT TO the view - a JavaBean
 * (private fields, public no-argument constructor, getters/setters). Add and change-password
 * fill the message; login fills the username and the name of the welcome screen.
 *
 * @author HE176322
 */
public class AccountResponseDTO {

    // The one-line result, e.g. "Password has been changed."; null for the welcome screen.
    private String message;

    // Username in its stored spelling, for "Hello <username>"; null for a one-line result.
    private String username;

    // Full name, for "Hi <name>, do you want change password now? Y/N:".
    private String name;

    // JavaBean constructor: an empty answer, filled through the setters.
    public AccountResponseDTO() {
    }

    // Returns the one-line result.
    public String getMessage() {
        return message;
    }

    // Sets the one-line result.
    public void setMessage(String message) {
        this.message = message;
    }

    // Returns the username.
    public String getUsername() {
        return username;
    }

    // Sets the username.
    public void setUsername(String username) {
        this.username = username;
    }

    // Returns the name.
    public String getName() {
        return name;
    }

    // Sets the name.
    public void setName(String name) {
        this.name = name;
    }
}
