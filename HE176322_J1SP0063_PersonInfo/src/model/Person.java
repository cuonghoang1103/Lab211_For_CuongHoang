package model;

import constants.Constants;

/**
 * MODEL: one person - name, address, salary - exactly the class the brief describes
 * ("private String name; private String address; private double salary", constructors,
 * get/set).
 *
 * @author HE176322
 */
public class Person {

    // Name of the person.
    private String name;

    // Address of the person.
    private String address;

    // Salary; the list is sorted by it.
    private double salary;

    // Creates an empty person (JavaBean constructor).
    public Person() {
    }

    // The brief's constructor: name, address, salary.
    public Person(String name, String address, double salary) {
        this.name = name;
        this.address = address;
        this.salary = salary;
    }

    // Returns the name.
    public String getName() {
        return name;
    }

    // Changes the name.
    public void setName(String name) {
        this.name = name;
    }

    // Returns the address.
    public String getAddress() {
        return address;
    }

    // Changes the address.
    public void setAddress(String address) {
        this.address = address;
    }

    // Returns the salary.
    public double getSalary() {
        return salary;
    }

    // Changes the salary.
    public void setSalary(double salary) {
        this.salary = salary;
    }

    // Polymorphism: overrides Object.toString(); returns the text instead of printing it,
    // because the model is not allowed to print.
    @Override
    public String toString() {
        return String.format(Constants.PERSON_FORMAT, name, address, salary);
    }
}
