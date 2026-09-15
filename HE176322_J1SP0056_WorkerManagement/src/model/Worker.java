package model;

/**
 * MODEL: describes one worker, and nothing else - a JavaBean (private fields, public
 * no-argument constructor, getters/setters), as in MVC of JSP.
 *
 * @author HE176322
 */
public class Worker {

    // Unique code of the worker.
    private String code;
    // Full name of the worker.
    private String name;
    // Age in years; the brief requires 18 to 50.
    private int age;
    // Current salary; changed by every up/down adjustment.
    private double salary;
    // Where the worker works.
    private String workLocation;

    // JavaBean constructor: an empty worker, filled through the setters.
    public Worker() {
    }

    // Creates a worker with every field filled in.
    public Worker(String code, String name, int age, double salary,
            String workLocation) {
        this.code = code;
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.workLocation = workLocation;
    }

    // Returns the code.
    public String getCode() {
        return code;
    }

    // Changes the code.
    public void setCode(String code) {
        this.code = code;
    }

    // Returns the name.
    public String getName() {
        return name;
    }

    // Changes the name.
    public void setName(String name) {
        this.name = name;
    }

    // Returns the age.
    public int getAge() {
        return age;
    }

    // Changes the age.
    public void setAge(int age) {
        this.age = age;
    }

    // Returns the current salary.
    public double getSalary() {
        return salary;
    }

    // Changes the salary.
    public void setSalary(double salary) {
        this.salary = salary;
    }

    // Returns the work location.
    public String getWorkLocation() {
        return workLocation;
    }

    // Changes the work location.
    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }

    // Polymorphism: overrides Object.toString().
    @Override
    public String toString() {
        return code + " - " + name;
    }
}
