package dto;

/**
 * DTO carrying what the user typed, FROM main INTO the controller: a user name and a
 * password, for both "create" and "login".
 *
 * @author HE176322
 */
public class AccountRequestDTO {

    // User name typed by the user (already validated).
    private String username;
    // Password typed by the user (already validated).
    private String password;

    // Creates an empty request; main fills it through the setters.
    public AccountRequestDTO() {
    }

    // Returns the user name.
    public String getUsername() {
        return username;
    }

    // Sets the user name.
    public void setUsername(String username) {
        this.username = username;
    }

    // Returns the password.
    public String getPassword() {
        return password;
    }

    // Sets the password.
    public void setPassword(String password) {
        this.password = password;
    }
}
