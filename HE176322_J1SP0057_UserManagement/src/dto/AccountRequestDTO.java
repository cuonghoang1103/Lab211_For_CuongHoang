package dto;

import java.util.ArrayList;

/**
 * DTO carrying data FROM main INTO the controller: a user name and a password (for both
 * "create" and "login"), or the lines main read from user.dat at start.
 *
 * @author HE176322
 */
public class AccountRequestDTO {

    // User name typed by the user (already validated).
    private String username;

    // Password typed by the user (already validated).
    private String password;

    // The lines of user.dat, read by main at start ("username password" each).
    private ArrayList<String> lineList;

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

    // Returns the lines of user.dat.
    public ArrayList<String> getLineList() {
        return lineList;
    }

    // Sets the lines of user.dat.
    public void setLineList(ArrayList<String> lineList) {
        this.lineList = lineList;
    }
}
