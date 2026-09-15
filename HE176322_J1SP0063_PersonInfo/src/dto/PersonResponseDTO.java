package dto;

/**
 * DTO carrying one person FROM the controller OUT TO the view.
 *
 * @author HE176322
 */
public class PersonResponseDTO {

    // Name shown after "Name:".
    private String name;
    // Address shown after "Address:".
    private String address;
    // Salary shown after "Salary:".
    private double salary;

    // Creates an empty response (JavaBean constructor).
    public PersonResponseDTO() {
    }

    // Creates the response with every value filled in.
    public PersonResponseDTO(String name, String address, double salary) {
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
