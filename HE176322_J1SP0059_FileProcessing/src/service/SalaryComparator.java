package service;

import java.util.Comparator;
import model.Person;

/**
 * CONCRETE STRATEGY (design pattern Strategy): the order "least money first, most money
 * last".
 *
 * @author HE176322
 */
public class SalaryComparator implements Comparator<Person> {

    // Compares two persons by salary.
    @Override
    public int compare(Person first, Person second) {
        return Double.compare(first.getSalary(), second.getSalary());
    }
}
