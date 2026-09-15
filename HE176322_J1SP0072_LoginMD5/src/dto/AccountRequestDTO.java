package dto;

/**
 * DTO carrying what the user typed, FROM main INTO the controller.
 *
 * @author HE176322
 */
public class AccountRequestDTO {

    // Username typed.
    private String username;
    // Password typed (plain text, hashed by the repository).
    private String password;
    // Name typed.
    private String name;
    // Phone typed.
    private String phone;
    // Email typed.
    private String email;
    // Address typed.
    private String address;
    // Date of birth typed (dd/MM/yyyy expected).
    private String dob;
    // Old password typed on the change screen.
    private String oldPassword;
    // New password typed.
    private String newPassword;
    // New password typed again.
    private String renewPassword;

    // Creates an empty request; main fills it through the setters.
    public AccountRequestDTO() {
    }

    // Returns the username typed.
    public String getUsername() {
        return username;
    }

    // Sets the username typed.
    public void setUsername(String username) {
        this.username = username;
    }

    // Returns the password typed (plain text, hashed by the repository).
    public String getPassword() {
        return password;
    }

    // Sets the password typed (plain text, hashed by the repository).
    public void setPassword(String password) {
        this.password = password;
    }

    // Returns the name typed.
    public String getName() {
        return name;
    }

    // Sets the name typed.
    public void setName(String name) {
        this.name = name;
    }

    // Returns the phone typed.
    public String getPhone() {
        return phone;
    }

    // Sets the phone typed.
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Returns the email typed.
    public String getEmail() {
        return email;
    }

    // Sets the email typed.
    public void setEmail(String email) {
        this.email = email;
    }

    // Returns the address typed.
    public String getAddress() {
        return address;
    }

    // Sets the address typed.
    public void setAddress(String address) {
        this.address = address;
    }

    // Returns the date of birth typed (dd/MM/yyyy expected).
    public String getDob() {
        return dob;
    }

    // Sets the date of birth typed (dd/MM/yyyy expected).
    public void setDob(String dob) {
        this.dob = dob;
    }

    // Returns the old password typed on the change screen.
    public String getOldPassword() {
        return oldPassword;
    }

    // Sets the old password typed on the change screen.
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    // Returns the new password typed.
    public String getNewPassword() {
        return newPassword;
    }

    // Sets the new password typed.
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    // Returns the new password typed again.
    public String getRenewPassword() {
        return renewPassword;
    }

    // Sets the new password typed again.
    public void setRenewPassword(String renewPassword) {
        this.renewPassword = renewPassword;
    }
}
