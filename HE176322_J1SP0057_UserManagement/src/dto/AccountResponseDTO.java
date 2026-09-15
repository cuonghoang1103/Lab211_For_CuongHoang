package dto;

/**
 * DTO carrying the account that logged in, FROM the controller OUT TO the view.
 *
 * @author HE176322
 */
public class AccountResponseDTO {

    // User name of the account that logged in.
    private String username;

    // Creates an empty response (JavaBean constructor).
    public AccountResponseDTO() {
    }

    // Creates the response for one account.
    public AccountResponseDTO(String username) {
        this.username = username;
    }

    // Returns the user name.
    public String getUsername() {
        return username;
    }

    // Sets the user name.
    public void setUsername(String username) {
        this.username = username;
    }
}
