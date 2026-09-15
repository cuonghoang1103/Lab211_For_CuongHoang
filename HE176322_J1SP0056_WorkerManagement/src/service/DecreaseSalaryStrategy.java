package service;

/**
 * CONCRETE STRATEGY: option 3 "Down salary" - the amount is subtracted.
 *
 * @author HE176322
 */
public class DecreaseSalaryStrategy implements SalaryStrategy {

    // Subtracts the amount from the salary.
    @Override
    public double adjust(double salary, double amount) {
        return salary - amount;
    }
}
