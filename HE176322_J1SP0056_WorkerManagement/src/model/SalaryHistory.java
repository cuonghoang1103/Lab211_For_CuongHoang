package model;

import constants.Constants;
import constants.SalaryStatus;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import utils.FormatUtils;

/**
 * MODEL: one line of the salary log - which worker, the salary AFTER the change, which
 * way it moved, and on which day.
 *
 * @author HE176322
 */
public class SalaryHistory {

    // The worker whose salary was adjusted.
    private Worker worker;

    // The salary right after this adjustment.
    private double salary;

    // UP for a raise, DOWN for a cut.
    private SalaryStatus status;

    // The day the adjustment was made.
    private LocalDate date;

    // JavaBean constructor: an empty line, filled through the setters.
    public SalaryHistory() {
    }

    // Creates a complete log line.
    public SalaryHistory(Worker worker, double salary, SalaryStatus status,
            LocalDate date) {
        this.worker = worker;
        this.salary = salary;
        this.status = status;
        this.date = date;
    }

    // Returns the worker.
    public Worker getWorker() {
        return worker;
    }

    // Changes the worker.
    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    // Returns the salary after the adjustment.
    public double getSalary() {
        return salary;
    }

    // Changes the salary after the adjustment.
    public void setSalary(double salary) {
        this.salary = salary;
    }

    // Returns the direction of the adjustment.
    public SalaryStatus getStatus() {
        return status;
    }

    // Changes the direction of the adjustment.
    public void setStatus(SalaryStatus status) {
        this.status = status;
    }

    // Returns the day of the adjustment.
    public LocalDate getDate() {
        return date;
    }

    // Changes the day of the adjustment.
    public void setDate(LocalDate date) {
        this.date = date;
    }

    // Polymorphism: overrides Object.toString() - one row of the brief's table, already
    // padded into fixed-width columns (Guide: "cần output gì thì thêm hàm toString()"), so
    // the view only has to print it: code, name, age, salary as "1100", UP/DOWN, dd/MM/yyyy.
    @Override
    public String toString() {
        return String.format(Constants.ROW_FORMAT, worker.getCode(), worker.getName(),
                worker.getAge(), FormatUtils.formatMoney(salary), status,
                date.format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)));
    }
}
