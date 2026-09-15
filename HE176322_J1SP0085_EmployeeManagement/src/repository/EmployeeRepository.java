package repository;

import dto.EmployeeRequestDTO;
import dto.EmployeeResponseDTO;
import java.util.ArrayList;
import model.Employee;
import model.EmployeeBuilder;
import utils.FormatUtils;

/**
 * REPOSITORY: holds the employees and performs the CRUD of the brief on them - add, find,
 * update, remove - plus the search by name.
 *
 * @author HE176322
 */
public class EmployeeRepository {

    // The "database" of employees, in the order they were added.
    private ArrayList<Employee> employees = new ArrayList<>();

    // Creates an empty repository.
    public EmployeeRepository() {
    }

    // Tells whether no employee is stored.
    public boolean isEmpty() {
        return employees.isEmpty();
    }

    // Tells whether an employee with this Id is stored (any case).
    public boolean isExistEmployee(EmployeeRequestDTO requestDTO) {
        return findById(requestDTO.getId()) != null;
    }

    // Stores a new employee built from the request with the Builder.
    public void addEmployee(EmployeeRequestDTO requestDTO) {
        Employee employee = new EmployeeBuilder()
                .withId(requestDTO.getId())
                .withFirstName(requestDTO.getFirstName())
                .withLastName(requestDTO.getLastName())
                .withPhone(requestDTO.getPhone())
                .withEmail(requestDTO.getEmail())
                .withAddress(requestDTO.getAddress())
                .withDob(requestDTO.getDob())
                .withSex(requestDTO.getSex())
                .withSalary(requestDTO.getSalary())
                .withAgency(requestDTO.getAgency())
                .build();
        employees.add(employee);
    }

    // Finds the employee with the request's Id.
    public EmployeeResponseDTO findEmployee(EmployeeRequestDTO requestDTO) {
        Employee employee = findById(requestDTO.getId());
        // unknown Id: nothing to return
        if (employee == null) {
            return null;
        }
        return toResponse(employee);
    }

    // Replaces the nine changeable fields of an employee.
    public EmployeeResponseDTO updateEmployee(EmployeeRequestDTO requestDTO) {
        Employee employee = findById(requestDTO.getId());
        // unknown Id: nothing to update
        if (employee == null) {
            return null;
        }
        employee.setFirstName(requestDTO.getFirstName());
        employee.setLastName(requestDTO.getLastName());
        employee.setPhone(requestDTO.getPhone());
        employee.setEmail(requestDTO.getEmail());
        employee.setAddress(requestDTO.getAddress());
        employee.setDob(requestDTO.getDob());
        employee.setSex(requestDTO.getSex());
        employee.setSalary(requestDTO.getSalary());
        employee.setAgency(requestDTO.getAgency());
        return toResponse(employee);
    }

    // Removes the employee with the request's Id.
    public boolean removeEmployee(EmployeeRequestDTO requestDTO) {
        Employee employee = findById(requestDTO.getId());
        // unknown Id: nothing removed
        if (employee == null) {
            return false;
        }
        return employees.remove(employee);
    }

    // The brief's searchByName: employees whose first OR last name contains the text,
    // ignoring upper/lower case ("sm" finds Smith, "JO" finds John).
    public ArrayList<EmployeeResponseDTO> searchByName(EmployeeRequestDTO requestDTO) {
        String keyword = requestDTO.getKeyword().toLowerCase();
        ArrayList<EmployeeResponseDTO> result = new ArrayList<>();
        // look at every employee once
        for (Employee employee : employees) {
            // keep the employee when either name contains the text
            if (employee.getFirstName().toLowerCase().contains(keyword)
                    || employee.getLastName().toLowerCase().contains(keyword)) {
                result.add(toResponse(employee));
            }
        }
        return result;
    }

    // A COPY of the list, for the service to sort.
    public ArrayList<Employee> getEmployees() {
        return new ArrayList<>(employees);
    }

    // Copies a model object into the DTO the view may see, with the date as text.
    public EmployeeResponseDTO toResponse(Employee employee) {
        EmployeeResponseDTO response = new EmployeeResponseDTO();
        response.setId(employee.getId());
        response.setFirstName(employee.getFirstName());
        response.setLastName(employee.getLastName());
        response.setPhone(employee.getPhone());
        response.setEmail(employee.getEmail());
        response.setAddress(employee.getAddress());
        response.setDob(FormatUtils.formatDate(employee.getDob()));
        response.setSex(employee.getSex());
        response.setSalary(employee.getSalary());
        response.setAgency(employee.getAgency());
        return response;
    }

    // Finds an employee by Id, ignoring upper/lower case.
    private Employee findById(String id) {
        // look at every employee once
        for (Employee employee : employees) {
            // same Id, whatever the case
            if (employee.getId().equalsIgnoreCase(id)) {
                return employee;
            }
        }
        return null;
    }
}
