package model;

import java.util.Date;

/**
 * BUILDER (design pattern): assembles an Employee step by step. Each step is a setter that
 * returns the builder itself, so the steps can be chained.
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

    // Sets the Id; returns the builder for the next step.
    public EmployeeBuilder setId(String id) {
        employee.setId(id);
        return this;
    }

    // Sets the first name; returns the builder for the next step.
    public EmployeeBuilder setFirstName(String firstName) {
        employee.setFirstName(firstName);
        return this;
    }

    // Sets the last name; returns the builder for the next step.
    public EmployeeBuilder setLastName(String lastName) {
        employee.setLastName(lastName);
        return this;
    }

    // Sets the phone; returns the builder for the next step.
    public EmployeeBuilder setPhone(String phone) {
        employee.setPhone(phone);
        return this;
    }

    // Sets the email; returns the builder for the next step.
    public EmployeeBuilder setEmail(String email) {
        employee.setEmail(email);
        return this;
    }

    // Sets the address; returns the builder for the next step.
    public EmployeeBuilder setAddress(String address) {
        employee.setAddress(address);
        return this;
    }

    // Sets the date of birth; returns the builder for the next step.
    public EmployeeBuilder setDob(Date dob) {
        employee.setDob(dob);
        return this;
    }

    // Sets the sex; returns the builder for the next step.
    public EmployeeBuilder setSex(String sex) {
        employee.setSex(sex);
        return this;
    }

    // Sets the salary; returns the builder for the next step.
    public EmployeeBuilder setSalary(double salary) {
        employee.setSalary(salary);
        return this;
    }

    // Sets the agency; returns the builder for the next step.
    public EmployeeBuilder setAgency(String agency) {
        employee.setAgency(agency);
        return this;
    }

    // Finishes the employee.
    public Employee build() {
        return employee;
    }
}
