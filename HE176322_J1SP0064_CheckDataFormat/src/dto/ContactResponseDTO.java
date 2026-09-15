package dto;

/**
 * DTO carrying the accepted values FROM the controller OUT TO the view, as the text the
 * screen shows.
 *
 * @author HE176322
 */
public class ContactResponseDTO {

    // Phone number to show.
    private String phone;
    // Email to show.
    private String email;
    // Date to show, formatted dd/MM/yyyy.
    private String date;

    // Creates an empty response; the service fills it through the setters.
    public ContactResponseDTO() {
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
