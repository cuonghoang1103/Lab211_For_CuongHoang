package model;

/**
 * MODEL: everybody in employee.dat - staff and manager in one file. Abstract: each person
 * is an Employee or a Manager, and the subclass answers the role questions.
 *
 * @author HE176322
 */
public abstract class Person implements Identifiable {

    // Employee id: no setter, it cannot change after created (the brief's Function 0).
    private String employeeID;
    // Full name.
    private String name;
    // Birthdate as the file holds it, e.g. 12/06/2000.
    private String birthdate;
    // male / female.
    private String sex;
    // MD5 hash of the password - never the password itself.
    private String password;

    // JavaBean constructor; only subclasses call it.
    protected Person() {
    }

    // Creates a person with every field filled in.
    protected Person(String employeeID, String name, String birthdate, String sex,
            String password) {
        this.employeeID = employeeID;
        this.name = name;
        this.birthdate = birthdate;
        this.sex = sex;
        this.password = password;
    }

    // The role code written in the file: EM or MA.
    public abstract String getRole();

    // What this person is called on screen: Employee or Manager.
    public abstract String getTitle();

    // May this person use the manager's functions? Asked of the object (polymorphism).
    public abstract boolean canManage();

    // The key of employee.dat.
    @Override
    public String getId() {
        return employeeID;
    }

    // Returns the employee id.
    public String getEmployeeID() {
        return employeeID;
    }

    // Returns the name.
    public String getName() {
        return name;
    }

    // Changes the name.
    public void setName(String name) {
        this.name = name;
    }

    // Returns the birthdate.
    public String getBirthdate() {
        return birthdate;
    }

    // Changes the birthdate.
    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    // Returns the sex.
    public String getSex() {
        return sex;
    }

    // Changes the sex.
    public void setSex(String sex) {
        this.sex = sex;
    }

    // Returns the MD5 hash of the password.
    public String getPassword() {
        return password;
    }

    // Changes the MD5 hash of the password.
    public void setPassword(String password) {
        this.password = password;
    }
}
