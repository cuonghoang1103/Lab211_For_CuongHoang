package service;

/**
 * CONCRETE STRATEGY: option 2 "Up salary" - the amount is added.
 *
 * @author HE176322
 */
public class IncreaseSalaryStrategy implements SalaryStrategy {

    // Adds the amount to the salary.
    @Override
    public double adjust(double salary, double amount) {
        return salary + amount;
    }
}
