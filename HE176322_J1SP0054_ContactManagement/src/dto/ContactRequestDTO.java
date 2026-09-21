package dto;

/**
 * DTO carrying what the user typed, FROM main INTO the controller.
 *
 * @author HE176322
 */
public class ContactRequestDTO {

    // ID typed on delete.
    private int id;

    // Full name typed on add.
    private String fullName;

    // Group typed on add.
    private String group;

    // Address typed on add.
    private String address;

    // Phone typed on add, already in a legal format.
    private String phone;

    // Creates an empty request; main fills it through the setters.
    public ContactRequestDTO() {
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
}
