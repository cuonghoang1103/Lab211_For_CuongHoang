package dto;

/**
 * DTO carrying ONE person (name, address, salary) between the layers: inside the request
 * (typed by the user, main -> controller) and inside the response (sorted, controller ->
 * view). A JavaBean; the view never sees the model Person itself.
 *
 * @author HE176322
 */
public class PersonDTO {

    // Name typed by the user (not blank).
    private String name;

    // Address typed by the user (not blank).
    private String address;

    // Salary typed by the user, already converted and checked by main.
    private double salary;

    // Creates an empty row (JavaBean constructor); filled through the setters.
    public PersonDTO() {
    }

    // Creates the row with every value filled in.
    public PersonDTO(String name, String address, double salary) {
        this.name = name;
        this.address = address;
        this.salary = salary;
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
