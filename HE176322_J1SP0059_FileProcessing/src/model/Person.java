package model;

import constants.Constants;

/**
 * MODEL: one person of the data file - name, address and salary - and nothing else.
 *
 * @author HE176322
 */
public class Person {

    // Name of the person.
    private String name;
    // Address of the person.
    private String address;
    // Salary; a double, not a String, because the program COMPARES and SORTS salaries,
    // and as text "1000" would come before "700".
    private double salary;

    // Creates an empty person, to be filled through the setters.
    public Person() {
    }

    // Creates a person with every field filled in.
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

    // Polymorphism: overrides Object.toString() to give the person in the format of the
    // data file, "name;address;salary".
    @Override
    public String toString() {
        return name + Constants.SEPARATOR + address + Constants.SEPARATOR + salary;
    }
}
