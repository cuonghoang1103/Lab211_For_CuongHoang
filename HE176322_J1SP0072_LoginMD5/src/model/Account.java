package model;

import constants.Constants;
import java.util.Date;

/**
 * MODEL: one account of the brief - username, password, name, phone, email, address, date
 * of birth - plus the id addAccount returns. The password is only ever the MD5 digest main
 * computed.
 *
 * @author HE176322
 */
public class Account {

    // Unique id; the repository gives it (last id + 1).
    private int id;

    // Login name; unique, compared without regard to case.
    private String username;

    // MD5 digest of the password (32 hex digits); the plain text is never kept.
    private String password;

    // Full name, used in "Hi ".
    private String name;

    // Phone number: 10 or 11 digits.
    private String phone;

    // Email address.
    private String email;

    // Address (the brief puts no rule on it).
    private String address;

    // Date of birth, parsed from dd/MM/yyyy.
    private Date dob;

    // JavaBean constructor: an empty account, filled by AccountBuilder.
    public Account() {
    }

    // Returns the id.
    public int getId() {
        return id;
    }

    // Sets the id.
    public void setId(int id) {
        this.id = id;
    }

    // Returns the username.
    public String getUsername() {
        return username;
    }

    // Sets the username.
    public void setUsername(String username) {
        this.username = username;
    }

    // Returns the password (MD5 digest).
    public String getPassword() {
        return password;
    }

    // Sets the password (MD5 digest).
    public void setPassword(String password) {
        this.password = password;
    }

    // Returns the name.
    public String getName() {
        return name;
    }

    // Sets the name.
    public void setName(String name) {
        this.name = name;
    }

    // Returns the phone.
    public String getPhone() {
        return phone;
    }

    // Sets the phone.
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Returns the email.
    public String getEmail() {
        return email;
    }

    // Sets the email.
    public void setEmail(String email) {
        this.email = email;
    }

    // Returns the address.
    public String getAddress() {
        return address;
    }

    // Sets the address.
    public void setAddress(String address) {
        this.address = address;
    }

    // Returns the date of birth.
    public Date getDob() {
        return dob;
    }

    // Sets the date of birth.
    public void setDob(Date dob) {
        this.dob = dob;
    }

    // Polymorphism: overrides Object.toString() so an account reads as one line in the
    // debugger (id, username, name - never the digest).
    @Override
    public String toString() {
        return String.format(Constants.ACCOUNT_FORMAT, id, username, name);
    }
}
