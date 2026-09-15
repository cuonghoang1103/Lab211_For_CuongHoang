package dto;

import constants.Constants;
import utils.FormatUtils;

/**
 * DTO carrying one employee FROM the controller OUT TO the view (and to main, which shows
 * the old values in brackets on Update) - a JavaBean.
 *
 * @author HE176322
 */
public class EmployeeResponseDTO {

    // ID.
    private String id;
    // First name.
    private String firstName;
    // Last name.
    private String lastName;
    // Phone.
    private String phone;
    // Email.
    private String email;
    // Address.
    private String address;
    // Date of birth as yyyy-MM-dd.
    private String dob;
    // Sex.
    private String sex;
    // Salary.
    private double salary;
    // Agency.
    private String agency;

    // JavaBean constructor: an empty response, filled through the setters.
    public EmployeeResponseDTO() {
    }

    // Returns the ID.
    public String getId() {
        return id;
    }

    // Sets the ID.
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

    // Returns the address.
    public String getAddress() {
        return address;
    }

    // Sets the address.
    public void setAddress(String address) {
        this.address = address;
    }

    // Returns the date of birth.
    public String getDob() {
        return dob;
    }

    // Sets the date of birth.
    public void setDob(String dob) {
        this.dob = dob;
    }

    // Returns the sex.
    public String getSex() {
        return sex;
    }

    // Sets the sex.
    public void setSex(String sex) {
        this.sex = sex;
    }

    // Returns the salary.
    public double getSalary() {
        return salary;
    }

    // Sets the salary.
    public void setSalary(double salary) {
        this.salary = salary;
    }

    // Returns the agency.
    public String getAgency() {
        return agency;
    }

    // Sets the agency.
    public void setAgency(String agency) {
        this.agency = agency;
    }

    // The "Name" column of the sorted list: first and last name together.
    public String getFullName() {
        return firstName + " " + lastName;
    }

    // The salary written like the brief's screen: two decimals, a point.
    public String getSalaryText() {
        return FormatUtils.formatSalary(salary);
    }

    // Every field on one line, shown after an update.
    @Override
    public String toString() {
        return String.format(Constants.EMPLOYEE_FORMAT, id, getFullName(), phone,
                email, address, dob, sex, getSalaryText(), agency);
    }
}
