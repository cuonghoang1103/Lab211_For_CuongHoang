package model;

import constants.CandidateType;
import constants.Constants;

/**
 * MODEL - the SUPERCLASS of the brief ("Should create Candidate as a SuperClass"):
 * everything every candidate has - id, first name, last name, birth date, address, phone,
 * email - written ONCE.
 *
 * @author HE176322
 */
public abstract class Candidate {

    // Unique id.
    private String id;

    // First name.
    private String firstName;

    // Last name.
    private String lastName;

    // Birth year, 1900..current year (the brief calls it Birth Date).
    private int birthDate;

    // Address.
    private String address;

    // Phone: kept as text so the leading 0 is not lost.
    private String phone;

    // Email in the form account@domain.
    private String email;

    // JavaBean constructor.
    protected Candidate() {
    }

    // Which of the three kinds this candidate is.
    public abstract CandidateType getCandidateType();

    // Template Method step: the columns only this kind of candidate has.
    protected abstract String getExtraInfo();

    // Returns the id.
    public String getId() {
        return id;
    }

    // Sets the id.
    public void setId(String id) {
        this.id = id;
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

    // Returns the birth year.
    public int getBirthDate() {
        return birthDate;
    }

    // Sets the birth year.
    public void setBirthDate(int birthDate) {
        this.birthDate = birthDate;
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

    // Returns the email.
    public String getEmail() {
        return email;
    }

    // Sets the email.
    public void setEmail(String email) {
        this.email = email;
    }

    // The brief's "Candidate name (First Name + Last Name)".
    public String getFullName() {
        return String.format(Constants.FULL_NAME_FORMAT, firstName, lastName);
    }

    // The search-result line of the brief: name, birth date, address, phone, email, type
    // - the same six columns whatever the kind.
    public String getSummary() {
        return String.format(Constants.SUMMARY_FORMAT, getFullName(), birthDate, address,
                phone, email, getCandidateType().getCode());
    }

    // TEMPLATE METHOD: the full line of the listing = the summary, then the subclass's
    // own columns.
    @Override
    public final String toString() {
        return String.format(Constants.DETAIL_FORMAT, getSummary(), getExtraInfo());
    }
}
