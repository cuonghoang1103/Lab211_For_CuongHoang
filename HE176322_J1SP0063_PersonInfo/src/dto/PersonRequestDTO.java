package dto;

/**
 * DTO carrying what the user typed for ONE person, FROM main INTO the controller.
 *
 * @author HE176322
 */
public class PersonRequestDTO {

    // Name typed by the user (not blank).
    private String name;
    // Address typed by the user (not blank).
    private String address;
    // Salary typed by the user, already converted and checked by main.
    private double salary;

    // Creates an empty request; main fills it through the setters.
    public PersonRequestDTO() {
    }

    // Returns the name.
    public String getName() {
        return name;
    }

    // Sets the name.
    public void setName(String name) {
        this.name = name;
    }

    // Returns the address.
    public String getAddress() {
        return address;
    }

    // Sets the address.
    public void setAddress(String address) {
        this.address = address;
    }

    // Returns the salary.
    public double getSalary() {
        return salary;
    }

    // Sets the salary.
    public void setSalary(double salary) {
        this.salary = salary;
    }
}
