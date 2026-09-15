package model;

import java.util.Date;

/**
 * BUILDER (design pattern): assembles an Employee step by step.
 *
 * @author HE176322
 */
public class EmployeeBuilder {

    // The employee being assembled; handed out by build().
    private Employee employee;

    // Starts a new, empty employee.
    public EmployeeBuilder() {
        employee = new Employee();
    }

    // Sets the ID.
    public EmployeeBuilder withId(String id) {
        employee.setId(id);
        return this;
    }

    // Sets the first name.
    public EmployeeBuilder withFirstName(String firstName) {
        employee.setFirstName(firstName);
        return this;
    }

    // Sets the last name.
    public EmployeeBuilder withLastName(String lastName) {
        employee.setLastName(lastName);
        return this;
    }

    // Sets the phone.
    public EmployeeBuilder withPhone(String phone) {
        employee.setPhone(phone);
        return this;
    }

    // Sets the email.
    public EmployeeBuilder withEmail(String email) {
        employee.setEmail(email);
        return this;
    }

    // Sets the address.
    public EmployeeBuilder withAddress(String address) {
        employee.setAddress(address);
        return this;
    }

    // Sets the date of birth.
    public EmployeeBuilder withDob(Date dob) {
        employee.setDob(dob);
        return this;
    }

    // Sets the sex.
    public EmployeeBuilder withSex(String sex) {
        employee.setSex(sex);
        return this;
    }

    // Sets the salary.
    public EmployeeBuilder withSalary(double salary) {
        employee.setSalary(salary);
        return this;
    }

    // Sets the agency.
    public EmployeeBuilder withAgency(String agency) {
        employee.setAgency(agency);
        return this;
    }

    // Finishes the employee.
    public Employee build() {
        return employee;
    }
}
