package model;

import java.util.Date;

/**
 * BUILDER (design pattern): assembles an Account step by step. Each step is a setter that
 * returns the builder itself, so the steps can be chained.
 *
 * @author HE176322
 */
public class AccountBuilder {

    // The account being assembled; handed out by build().
    private Account account;

    // Starts a new, empty account.
    public AccountBuilder() {
        account = new Account();
    }

    // Sets the id; returns the builder for the next step.
    public AccountBuilder setId(int id) {
        account.setId(id);
        return this;
    }

    // Sets the username; returns the builder for the next step.
    public AccountBuilder setUsername(String username) {
        account.setUsername(username);
        return this;
    }

    // Sets the password (the MD5 digest); returns the builder for the next step.
    public AccountBuilder setPassword(String password) {
        account.setPassword(password);
        return this;
    }

    // Sets the name; returns the builder for the next step.
    public AccountBuilder setName(String name) {
        account.setName(name);
        return this;
    }

    // Sets the phone; returns the builder for the next step.
    public AccountBuilder setPhone(String phone) {
        account.setPhone(phone);
        return this;
    }

    // Sets the email; returns the builder for the next step.
    public AccountBuilder setEmail(String email) {
        account.setEmail(email);
        return this;
    }

    // Sets the address; returns the builder for the next step.
    public AccountBuilder setAddress(String address) {
        account.setAddress(address);
        return this;
    }

    // Sets the date of birth; returns the builder for the next step.
    public AccountBuilder setDob(Date dob) {
        account.setDob(dob);
        return this;
    }

    // Finishes the account.
    public Account build() {
        return account;
    }
}
