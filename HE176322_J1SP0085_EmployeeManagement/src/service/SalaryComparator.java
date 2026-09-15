package service;

import java.util.Comparator;
import model.Employee;

/**
 * CONCRETE STRATEGY: orders employees by salary, lowest first.
 *
 * @author HE176322
 */
public class SalaryComparator implements Comparator<Employee> {

    // Compares two employees by salary.
    @Override
    public int compare(Employee first, Employee second) {
        return Double.compare(first.getSalary(), second.getSalary());
    }
}
