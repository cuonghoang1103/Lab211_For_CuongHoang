package dto;

/**
 * DTO carrying what the user typed FROM main INTO the controller: the phone, the email
 * and the date, each one already accepted by its check.
 *
 * @author HE176322
 */
public class ContactRequestDTO {

    // Phone number that passed checkPhone.
    private String phone;

    // Email that passed checkEmail.
    private String email;

    // Date text (dd/MM/yyyy) that passed checkDate.
    private String date;

    // Creates an empty request; main fills it through the setters.
    public ContactRequestDTO() {
    }

    // Returns the phone number.
    public String getPhone() {
        return phone;
    }

    // Sets the phone number.
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

    // Returns the date text.
    public String getDate() {
        return date;
    }

    // Sets the date text.
    public void setDate(String date) {
        this.date = date;
    }
}
