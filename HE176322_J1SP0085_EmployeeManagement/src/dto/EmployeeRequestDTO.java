package dto;

import java.util.Date;

/**
 * DTO carrying what the user typed (already checked by main) FROM main INTO the controller.
 * On Update it also carries the other way the stored values of the employee, which main
 * shows in brackets.
 *
 * @author HE176322
 */
public class EmployeeRequestDTO {

    // Id typed.
    private String id;

    // First name typed.
    private String firstName;

    // Last name typed.
    private String lastName;

    // Phone typed.
    private String phone;

    // Email typed.
    private String email;

    // Address typed.
    private String address;

    // Date of birth typed.
    private Date dob;

    // Sex typed.
    private String sex;

    // Salary typed.
    private double salary;

    // Agency typed.
    private String agency;

    // Search text typed.
    private String keyword;

    // Creates an empty request; main fills it through the setters.
    public EmployeeRequestDTO() {
    }

    // Returns the Id.
    public String getId() {
        return id;
    }

    // Sets the Id.
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
    public Date getDob() {
        return dob;
    }

    // Sets the date of birth.
    public void setDob(Date dob) {
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

    // Returns the search text.
    public String getKeyword() {
        return keyword;
    }

    // Sets the search text.
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
