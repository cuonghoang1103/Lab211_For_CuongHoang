package model;

/**
 * BUILDER (design pattern): builds a Contact step by step. Each step is a setter that
 * returns the builder itself, so the steps can be chained.
 *
 * @author HE176322
 */
public class ContactBuilder {

    // ID of the contact being built.
    private int id;

    // Full name of the contact being built.
    private String fullName;

    // Group of the contact being built.
    private String group;

    // Address of the contact being built.
    private String address;

    // Phone of the contact being built.
    private String phone;

    // Creates an empty builder (JavaBean-style public no-argument ctor).
    public ContactBuilder() {
    }

    // Sets the ID; returns the builder for the next step.
    public ContactBuilder setId(int id) {
        this.id = id;
        return this;
    }

    // Sets the full name; returns the builder for the next step.
    public ContactBuilder setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    // Sets the group; returns the builder for the next step.
    public ContactBuilder setGroup(String group) {
        this.group = group;
        return this;
    }

    // Sets the address; returns the builder for the next step.
    public ContactBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    // Sets the phone; returns the builder for the next step.
    public ContactBuilder setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    // Creates the Contact from every value given so far.
    public Contact build() {
        return new Contact(id, fullName, group, address, phone);
    }
}
