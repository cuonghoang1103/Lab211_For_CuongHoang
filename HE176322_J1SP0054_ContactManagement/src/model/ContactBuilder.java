package model;

/**
 * BUILDER (design pattern): builds a Contact step by step.
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

    // Sets the ID.
    public ContactBuilder withId(int id) {
        this.id = id;
        return this;
    }

    // Sets the full name.
    public ContactBuilder withFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    // Sets the group.
    public ContactBuilder withGroup(String group) {
        this.group = group;
        return this;
    }

    // Sets the address.
    public ContactBuilder withAddress(String address) {
        this.address = address;
        return this;
    }

    // Sets the phone.
    public ContactBuilder withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    // Creates the Contact from every value given so far.
    public Contact build() {
        return new Contact(id, fullName, group, address, phone);
    }
}
