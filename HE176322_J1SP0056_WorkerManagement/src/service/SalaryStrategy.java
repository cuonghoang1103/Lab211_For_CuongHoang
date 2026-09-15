package service;

/**
 * STRATEGY (design pattern): the common contract of every way to adjust a salary.
 *
 * @author HE176322
 */
public interface SalaryStrategy {

    // Computes the salary after the adjustment.
    double adjust(double salary, double amount);
}
