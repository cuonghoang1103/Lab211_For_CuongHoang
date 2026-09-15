package dto;

import constants.Constants;

/**
 * DTO carrying one contact FROM the controller OUT TO the view - a JavaBean.
 *
 * @author HE176322
 */
public class ContactResponseDTO {

    // ID column.
    private int id;
    // Name column (the full name).
    private String fullName;
    // First Name column.
    private String firstName;
    // Last Name column.
    private String lastName;
    // Group column.
    private String group;
    // Address column.
    private String address;
    // Phone column.
    private String phone;

    // JavaBean constructor: an empty row, filled through the setters.
    public ContactResponseDTO() {
    }

    // Returns the ID.
    public int getId() {
        return id;
    }

    // Sets the ID.
    public void setId(int id) {
        this.id = id;
    }

    // Returns the full name.
    public String getFullName() {
        return fullName;
    }

    // Sets the full name.
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    // Returns the first name.
    public String getFirstName() {
        return firstName;
    }

    // Sets the first name.
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Returns the last name.
    public String getLastName() {
        return lastName;
    }

    // Sets the last name.
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Returns the group.
    public String getGroup() {
        return group;
    }

    // Sets the group.
    public void setGroup(String group) {
        this.group = group;
    }

    // Returns the address.
    public String getAddress() {
        return address;
    }

    // Sets the address.
    public void setAddress(String address) {
        this.address = address;
    }

    // Returns the phone.
    public String getPhone() {
        return phone;
    }

    // Sets the phone.
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // One table row, already padded into fixed-width columns, so the view only has to
    // print it.
    @Override
    public String toString() {
        return String.format(Constants.ROW_FORMAT, id, fullName, firstName, lastName,
                group, address, phone);
    }
}
