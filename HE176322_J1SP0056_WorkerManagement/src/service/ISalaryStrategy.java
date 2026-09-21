package service;

/**
 * STRATEGY (design pattern): the common contract of every way to adjust a salary. An
 * interface, so its name starts with "I" (checklist 1.3).
 *
 * @author HE176322
 */
public interface ISalaryStrategy {

    // Calculates the salary after the adjustment.
    double calculateSalary(double salary, double amount);
}
