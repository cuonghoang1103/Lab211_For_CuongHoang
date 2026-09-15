package dto;

/**
 * DTO carrying the logged-in account FROM the controller OUT TO the view - a JavaBean
 * (private fields, public no-argument constructor, getters/setters).
 *
 * @author HE176322
 */
public class AccountResponseDTO {

    // Username, in its stored spelling.
    private String username;
    // Full name.
    private String name;

    // JavaBean constructor: an empty response, filled through the setters.
    public AccountResponseDTO() {
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
