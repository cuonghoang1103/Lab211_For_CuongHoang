package model;

import constants.Constants;

/**
 * MODEL: one row of the customer's CSV file - ID, Name, Email, Phone, Address - a
 * JavaBean (private fields, public no-argument constructor, getters/setters).
 *
 * @author HE176322
 */
public class Customer {

    // First column.
    private String id;
    // Second column.
    private String name;
    // Third column.
    private String email;
    // Fourth column.
    private String phone;
    // Fifth column.
    private String address;

    // JavaBean constructor: an empty row.
    public Customer() {
    }

    // Creates a row with every column filled in.
    public Customer(String id, String name, String email, String phone,
            String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    // Returns the ID.
    public String getId() {
        return id;
    }

    // Sets the ID.
    public void setId(String id) {
        this.id = id;
    }

    // Returns the name.
    public String getName() {
        return name;
    }

    // Sets the name.
    public void setName(String name) {
        this.name = name;
    }

    // Returns the email.
    public String getEmail() {
        return email;
    }

    // Sets the email.
    public void setEmail(String email) {
        this.email = email;
    }

    // Returns the phone.
    public String getPhone() {
        return phone;
    }

    // Sets the phone.
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Returns the address.
    public String getAddress() {
        return address;
    }

    // Sets the address.
    public void setAddress(String address) {
        this.address = address;
    }

    // Polymorphism: overrides Object.toString() to give the row back in the brief's CSV
    // form "1, Nguyen Van A, anv@gmail.com, 098889999, ...".
    @Override
    public String toString() {
        return id + Constants.FIELD_JOIN + name + Constants.FIELD_JOIN + email
                + Constants.FIELD_JOIN + phone + Constants.FIELD_JOIN + address;
    }
}
