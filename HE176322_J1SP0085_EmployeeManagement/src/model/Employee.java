package model;

import constants.Constants;
import java.util.Date;
import utils.FormatUtils;

/**
 * MODEL: one employee - the ten fields of the brief's class diagram (Id, First Name, Last
 * Name, Phone, Email, Address, DOB, Sex, Salary, Agency).
 *
 * @author HE176322
 */
public class Employee {

    // Unique Id.
    private String id;

    // First name.
    private String firstName;

    // Last name.
    private String lastName;

    // Phone number: digits only.
    private String phone;

    // Email address.
    private String email;

    // Address.
    private String address;

    // Date of birth, parsed from yyyy-MM-dd.
    private Date dob;

    // "Male" or "Female".
    private String sex;

    // Salary, greater than 0.
    private double salary;

    // Agency (department).
    private String agency;

    // JavaBean constructor: an empty employee, filled by EmployeeBuilder.
    public Employee() {
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

    // The full name as the tables show it: first name, a space, last name.
    private String formatFullName() {
        return String.format(Constants.FULL_NAME_FORMAT, firstName, lastName);
    }

    // One row of the brief's search table: Id, First name, Last name, Salary, Agency.
    public String formatSearchRow() {
        return String.format(Constants.SEARCH_ROW, id, firstName, lastName,
                FormatUtils.formatSalary(salary), agency);
    }

    // One row of the brief's list sorted by salary: Id, Name, Salary, Agency.
    public String formatSortRow() {
        return String.format(Constants.SORT_ROW, id, formatFullName(),
                FormatUtils.formatSalary(salary), agency);
    }

    // Polymorphism: overrides Object.toString() (the brief: "an overridden toString()") -
    // every field on one line, shown after an update.
    @Override
    public String toString() {
        return String.format(Constants.EMPLOYEE_FORMAT, id, formatFullName(), phone, email,
                address, FormatUtils.formatDate(dob), sex, FormatUtils.formatSalary(salary),
                agency);
    }
}
