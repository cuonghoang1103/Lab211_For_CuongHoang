package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import model.Employee;
import repository.EmployeeRepository;

/**
 * SERVICE and Strategy CONTEXT: sorts the employees with whatever Comparator it was given
 * (the brief: "order the list by salary with a Comparator"). Called only by the controller;
 * no print, no keyboard.
 *
 * @author HE176322
 */
public class EmployeeService {

    // Where the employees are stored (Service -> Repository -> Model).
    private EmployeeRepository employeeRepository;

    // The sort order, chosen by whoever creates this service (the Strategy).
    private Comparator<Employee> comparator;

    // Creates the service with the repository and the sort order.
    public EmployeeService(EmployeeRepository employeeRepository,
            Comparator<Employee> comparator) {
        this.employeeRepository = employeeRepository;
        this.comparator = comparator;
    }

    // The brief's sortBySalary: sorts a COPY of the list with the comparator, then gives
    // one row of the sorted list per employee.
    public ArrayList<String> sortBySalary() {
        ArrayList<Employee> sortedList = employeeRepository.getEmployeeList();
        ArrayList<String> rowList = new ArrayList<>();

        // Collections.sort knows HOW to sort; the comparator decides WHO comes first
        Collections.sort(sortedList, comparator);

        // one row per employee, in the new order
        for (Employee employee : sortedList) {
            rowList.add(employee.formatSortRow());
        }

        return rowList;
    }
}
