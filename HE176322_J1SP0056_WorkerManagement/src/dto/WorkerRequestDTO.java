package dto;

/**
 * DTO carrying the worker the user typed, FROM main INTO the controller (option 1).
 *
 * @author HE176322
 */
public class WorkerRequestDTO {

    // Code typed by the user; must be non-empty and unique.
    private String code;
    // Name typed by the user.
    private String name;
    // Age typed by the user; checked against 18..50 by the service.
    private int age;
    // Salary typed by the user; must be greater than 0.
    private double salary;
    // Work location typed by the user.
    private String workLocation;

    // Creates an empty request; main fills it through the setters.
    public WorkerRequestDTO() {
    }

    // Returns the code.
    public String getCode() {
        return code;
    }

    // Sets the code.
    public void setCode(String code) {
        this.code = code;
    }

    // Returns the name.
    public String getName() {
        return name;
    }

    // Sets the name.
    public void setName(String name) {
        this.name = name;
    }

    // Returns the age.
    public int getAge() {
        return age;
    }

    // Sets the age.
    public void setAge(int age) {
        this.age = age;
    }

    // Returns the salary.
    public double getSalary() {
        return salary;
    }

    // Sets the salary.
    public void setSalary(double salary) {
        this.salary = salary;
    }

    // Returns the work location.
    public String getWorkLocation() {
        return workLocation;
    }

    // Sets the work location.
    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }
}
