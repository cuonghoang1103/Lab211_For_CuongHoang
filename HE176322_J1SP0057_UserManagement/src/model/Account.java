package model;

import constants.Constants;

/**
 * MODEL: one user account - a user name and a password - and nothing else.
 *
 * @author HE176322
 */
public class Account {

    // User name: at least 5 characters, no space, unique in user.dat.
    private String username;
    // Password: at least 6 characters, no space.
    private String password;

    // Creates an empty account, to be filled through the setters.
    public Account() {
    }

    // Creates an account with both fields filled in.
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Returns the user name.
    public String getUsername() {
        return username;
    }

    // Changes the user name.
    public void setUsername(String username) {
        this.username = username;
    }

    // Returns the password.
    public String getPassword() {
        return password;
    }

    // Changes the password.
    public void setPassword(String password) {
        this.password = password;
    }

    // Tells whether this account has the given user name AND password - the object's own
    // behaviour, used by the repository's find.
    public boolean isMatch(Account other) {
        return username.equals(other.getUsername())
                && password.equals(other.getPassword());
    }

    // Polymorphism: overrides Object.toString() to give the account as ONE LINE OF
    // user.dat, "username password".
    @Override
    public String toString() {
        return username + Constants.SEPARATOR + password;
    }
}
