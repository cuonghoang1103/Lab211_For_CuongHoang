package repository;

import dto.EmployeeRequestDTO;
import java.util.ArrayList;
import model.Employee;
import model.EmployeeBuilder;

/**
 * REPOSITORY: holds the employees (the list of the brief's EmployeeManager) and the simple
 * CRUD of the brief on them - add, find, update, remove - plus the search by name. No
 * keyboard, no print; what the view shows leaves as the text of the model (toString and
 * its rows).
 *
 * @author HE176322
 */
public class EmployeeRepository {

    // brief: employees : List<Employee> - named with "List" (checklist 1.5). The employees,
    // in the order they were added.
    private ArrayList<Employee> employeeList;

    // Creates an empty repository.
    public EmployeeRepository() {
        employeeList = new ArrayList<>();
    }

    // Tells whether no employee is stored.
    public boolean isEmpty() {
        return employeeList.isEmpty();
    }

    // Tells whether an employee with this Id is stored (whatever the case).
    public boolean isExistEmployee(String id) {
        return findById(id) != null;
    }

    // The brief's addEmployee: stores a new employee, built from the request (every value
    // already checked by main) with the Builder.
    public void addEmployee(EmployeeRequestDTO requestDTO) {
        Employee employee = new EmployeeBuilder()
                .setId(requestDTO.getId())
                .setFirstName(requestDTO.getFirstName())
                .setLastName(requestDTO.getLastName())
                .setPhone(requestDTO.getPhone())
                .setEmail(requestDTO.getEmail())
                .setAddress(requestDTO.getAddress())
                .setDob(requestDTO.getDob())
                .setSex(requestDTO.getSex())
                .setSalary(requestDTO.getSalary())
                .setAgency(requestDTO.getAgency())
                .build();

        // Create of CRUD
        employeeList.add(employee);
    }

    // First step of Update: copies the stored values of the employee with the request's Id
    // into the request, so main can show them in brackets. False when no employee has it.
    public boolean loadEmployee(EmployeeRequestDTO requestDTO) {
        Employee employee = findById(requestDTO.getId());

        // unknown Id: nothing to load
        if (employee == null) {
            return false;
        }

        // the stored spelling of the Id, then the nine values the form offers to keep
        requestDTO.setId(employee.getId());
        requestDTO.setFirstName(employee.getFirstName());
        requestDTO.setLastName(employee.getLastName());
        requestDTO.setPhone(employee.getPhone());
        requestDTO.setEmail(employee.getEmail());
        requestDTO.setAddress(employee.getAddress());
        requestDTO.setDob(employee.getDob());
        requestDTO.setSex(employee.getSex());
        requestDTO.setSalary(employee.getSalary());
        requestDTO.setAgency(employee.getAgency());
        return true;
    }

    // The brief's updateEmployee(id): replaces the nine changeable fields of the employee
    // with the request's Id; returns it on one line (its toString), or null when no
    // employee has that Id.
    public String updateEmployee(EmployeeRequestDTO requestDTO) {
        Employee employee = findById(requestDTO.getId());

        // unknown Id: nothing to update
        if (employee == null) {
            return null;
        }

        // the Id itself never changes
        employee.setFirstName(requestDTO.getFirstName());
        employee.setLastName(requestDTO.getLastName());
        employee.setPhone(requestDTO.getPhone());
        employee.setEmail(requestDTO.getEmail());
        employee.setAddress(requestDTO.getAddress());
        employee.setDob(requestDTO.getDob());
        employee.setSex(requestDTO.getSex());
        employee.setSalary(requestDTO.getSalary());
        employee.setAgency(requestDTO.getAgency());
        return employee.toString();
    }

    // The brief's removeEmployee(id): deletes the employee with this Id; false when no
    // employee has it.
    public boolean removeEmployee(String id) {
        Employee employee = findById(id);

        // unknown Id: nothing removed
        if (employee == null) {
            return false;
        }

        return employeeList.remove(employee);
    }

    // The brief's searchByName(name): one row of the search table for every employee whose
    // first OR last name contains the text, ignoring upper/lower case ("sm" finds Smith).
    public ArrayList<String> searchByName(String name) {
        String keyword = name.toLowerCase();
        ArrayList<String> rowList = new ArrayList<>();

        // look at every employee once
        for (Employee employee : employeeList) {
            // keep the employee when either name contains the text
            if (employee.getFirstName().toLowerCase().contains(keyword) ||
                    employee.getLastName().toLowerCase().contains(keyword)) {
                rowList.add(employee.formatSearchRow());
            }
        }

        return rowList;
    }

    // A COPY of the list, for the service to sort: the stored order never changes.
    public ArrayList<Employee> getEmployeeList() {
        return new ArrayList<>(employeeList);
    }

    // Finds an employee by Id, ignoring upper/lower case; null when none.
    private Employee findById(String id) {
        // look at every employee once
        for (Employee employee : employeeList) {
            // same Id, whatever the case
            if (employee.getId().equalsIgnoreCase(id)) {
                return employee;
            }
        }

        return null;
    }
}
