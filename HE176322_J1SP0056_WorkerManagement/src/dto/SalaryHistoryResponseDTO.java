package dto;

import constants.Constants;
import utils.FormatUtils;

/**
 * DTO carrying one row of the salary table FROM the controller OUT TO the view.
 *
 * @author HE176322
 */
public class SalaryHistoryResponseDTO {

    // Worker code (column Code).
    private String code;
    // Worker name (column Name).
    private String name;
    // Worker age (column Age).
    private int age;
    // Salary after the adjustment (column Salary).
    private double salary;
    // "UP" or "DOWN" (column Status).
    private String status;
    // Date as dd/MM/yyyy (column Date).
    private String date;

    // JavaBean constructor: an empty row, filled through the setters.
    public SalaryHistoryResponseDTO() {
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

    // Returns the salary after the adjustment.
    public double getSalary() {
        return salary;
    }

    // Sets the salary after the adjustment.
    public void setSalary(double salary) {
        this.salary = salary;
    }

    // Returns the status text.
    public String getStatus() {
        return status;
    }

    // Sets the status text.
    public void setStatus(String status) {
        this.status = status;
    }

    // Returns the date text.
    public String getDate() {
        return date;
    }

    // Sets the date text.
    public void setDate(String date) {
        this.date = date;
    }

    // One table row padded into fixed-width columns, so the view only has to print it.
    @Override
    public String toString() {
        return String.format(Constants.ROW_FORMAT, code, name, age,
                FormatUtils.formatMoney(salary), status, date);
    }
}
