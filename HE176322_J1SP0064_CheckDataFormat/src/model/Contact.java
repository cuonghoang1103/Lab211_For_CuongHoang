package model;

import constants.Constants;
import java.util.Date;

/**
 * MODEL: the data the user finally entered - a phone number, an email and a date - with
 * the date kept as a real java.util.Date, not as text.
 *
 * @author HE176322
 */
public class Contact {

    // Phone number: exactly 10 digits; a String so a leading 0 is kept.
    private String phone;

    // Email address in name@domain.ext form.
    private String email;

    // The date the user typed, as a calendar date.
    private Date date;

    // Creates an empty contact (JavaBean constructor).
    public Contact() {
    }

    // Creates a contact with every value filled in.
    public Contact(String phone, String email, Date date) {
        this.phone = phone;
        this.email = email;
        this.date = date;
    }

    // Returns the phone number.
    public String getPhone() {
        return phone;
    }

    // Changes the phone number.
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Returns the email.
    public String getEmail() {
        return email;
    }

    // Changes the email.
    public void setEmail(String email) {
        this.email = email;
    }

    // Returns the date.
    public Date getDate() {
        return date;
    }

    // Changes the date.
    public void setDate(Date date) {
        this.date = date;
    }

    // Polymorphism: overrides Object.toString(); returns the text, never prints it.
    @Override
    public String toString() {
        return String.format(Constants.CONTACT_FORMAT, phone, email, date);
    }
}
