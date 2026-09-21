package dto;

import java.util.Date;

/**
 * DTO carrying what the user typed FROM main INTO the controller - every value already
 * checked by main, every password already turned into its MD5 digest by main.
 *
 * @author HE176322
 */
public class AccountRequestDTO {

    // Username typed.
    private String username;

    // MD5 digest of the password typed (the plain text never leaves main).
    private String password;

    // Name typed.
    private String name;

    // Phone typed (10 or 11 digits).
    private String phone;

    // Email typed.
    private String email;

    // Address typed.
    private String address;

    // Date of birth typed, already parsed from dd/MM/yyyy.
    private Date dob;

    // MD5 digest of the old password typed on the change screen.
    private String oldPassword;

    // MD5 digest of the new password typed (twice, the same) on the change screen.
    private String newPassword;

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

    // Returns the digest of the password.
    public String getPassword() {
        return password;
    }

    // Sets the digest of the password.
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

    // Returns the date of birth.
    public Date getDob() {
        return dob;
    }

    // Sets the date of birth.
    public void setDob(Date dob) {
        this.dob = dob;
    }

    // Returns the digest of the old password.
    public String getOldPassword() {
        return oldPassword;
    }

    // Sets the digest of the old password.
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    // Returns the digest of the new password.
    public String getNewPassword() {
        return newPassword;
    }

    // Sets the digest of the new password.
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
