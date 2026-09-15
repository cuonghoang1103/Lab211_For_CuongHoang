package model;

import constants.Constants;

/**
 * MODEL: one contact of the brief - ID, full name, first name, last name, group, address,
 * phone.
 *
 * @author HE176322
 */
public class Contact {

    // Unique ID, given by the repository (last ID + 1).
    private int id;
    // The full name as typed.
    private String fullName;
    // Everything before the first space.
    private String firstName;
    // Everything after the first space.
    private String lastName;
    // Group of the contact.
    private String group;
    // Address of the contact.
    private String address;
    // Phone, in one of the seven formats of the brief.
    private String phone;

    // JavaBean constructor: an empty contact, filled through the setters.
    public Contact() {
        applyFullName("");
    }

    // Creates a contact with every field filled in (used by ContactBuilder).
    public Contact(int id, String fullName, String group, String address, String phone) {
        this.id = id;
        applyFullName(fullName);
        this.group = group;
        this.address = address;
        this.phone = phone;
    }

    // Stores the full name and splits it at the FIRST space (the brief): "Raul Gonzalez"
    // gives "Raul" + "Gonzalez"; "Ronaldo de Assis" gives "Ronaldo" + "de Assis"; a
    // one-word name "Cher" gives "Cher" + "".
    private void applyFullName(String name) {
        fullName = name == null ? "" : name.trim();
        int space = fullName.indexOf(Constants.NAME_SEPARATOR);
        // one word only: it is the first name, the last name is empty
        if (space < 0) {
            firstName = fullName;
            lastName = "";
        } else {
            // before the first space / after it
            firstName = fullName.substring(0, space);
            lastName = fullName.substring(space + 1).trim();
        }
    }

    // Returns the ID.
    public int getId() {
        return id;
    }

    // Changes the ID.
    public void setId(int id) {
        this.id = id;
    }

    // Returns the full name.
    public String getFullName() {
        return fullName;
    }

    // Changes the full name; first and last name follow automatically.
    public void setFullName(String fullName) {
        applyFullName(fullName);
    }

    // Returns the first name (read-only: derived from the full name).
    public String getFirstName() {
        return firstName;
    }

    // Returns the last name (read-only: derived from the full name).
    public String getLastName() {
        return lastName;
    }

    // Returns the group.
    public String getGroup() {
        return group;
    }

    // Changes the group.
    public void setGroup(String group) {
        this.group = group;
    }

    // Returns the address.
    public String getAddress() {
        return address;
    }

    // Changes the address.
    public void setAddress(String address) {
        this.address = address;
    }

    // Returns the phone.
    public String getPhone() {
        return phone;
    }

    // Changes the phone.
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Polymorphism: overrides Object.toString() so the debugger shows the contact on one
    // line.
    @Override
    public String toString() {
        return id + " " + fullName;
    }
}
