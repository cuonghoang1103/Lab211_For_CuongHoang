package model;

import java.util.Date;

/**
 * BUILDER (design pattern): assembles an Account step by step.
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

    // Sets the ID.
    public AccountBuilder withId(int id) {
        account.setId(id);
        return this;
    }

    // Sets the username.
    public AccountBuilder withUsername(String username) {
        account.setUsername(username);
        return this;
    }

    // Sets the password (MD5 digest).
    public AccountBuilder withPassword(String password) {
        account.setPassword(password);
        return this;
    }

    // Sets the name.
    public AccountBuilder withName(String name) {
        account.setName(name);
        return this;
    }

    // Sets the phone.
    public AccountBuilder withPhone(String phone) {
        account.setPhone(phone);
        return this;
    }

    // Sets the email.
    public AccountBuilder withEmail(String email) {
        account.setEmail(email);
        return this;
    }

    // Sets the address.
    public AccountBuilder withAddress(String address) {
        account.setAddress(address);
        return this;
    }

    // Sets the date of birth.
    public AccountBuilder withDob(Date dob) {
        account.setDob(dob);
        return this;
    }

    // Finishes the account.
    public Account build() {
        return account;
    }
}
