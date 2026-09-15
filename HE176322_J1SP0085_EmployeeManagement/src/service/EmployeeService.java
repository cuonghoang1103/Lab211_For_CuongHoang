package service;

import dto.EmployeeResponseDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import model.Employee;
import repository.EmployeeRepository;

/**
 * SERVICE and Strategy CONTEXT: sorts the employees with whatever Comparator it was given
 * (the brief: "order the list by salary with a Comparator").
 *
 * @author HE176322
 */
public class EmployeeService {

    // Where the employees are stored.
    private EmployeeRepository employeeRepository;
    // The sort order, chosen by whoever creates this service.
    private Comparator<Employee> comparator;

    // Creates the service with the repository and the sort order.
    public EmployeeService(EmployeeRepository employeeRepository,
            Comparator<Employee> comparator) {
        this.employeeRepository = employeeRepository;
        this.comparator = comparator;
    }

    // The brief's sortBySalary: a sorted COPY of the list.
    public ArrayList<EmployeeResponseDTO> sortBySalary() {
        ArrayList<Employee> sorted = employeeRepository.getEmployees();
        Collections.sort(sorted, comparator);
        ArrayList<EmployeeResponseDTO> result = new ArrayList<>();
        // copy each sorted employee into the DTO the view is allowed to see
        for (Employee employee : sorted) {
            result.add(employeeRepository.toResponse(employee));
        }
        return result;
    }
}
