package controller;

import constants.Message;
import dto.EmployeeRequestDTO;
import dto.EmployeeResponseDTO;
import java.util.ArrayList;
import repository.EmployeeRepository;
import service.EmployeeService;
import service.SalaryComparator;
import view.EmployeeView;

/**
 * CONTROLLER (and FACADE for main): receives a request DTO from main, asks the repository
 * (CRUD, search) or the service (sort) to do the work, and hands the result to the view.
 *
 * @author HE176322
 */
public class EmployeeController {

    // Where the employees are stored.
    private EmployeeRepository employeeRepository;
    // Sorts the employees; configured with the salary order.
    private EmployeeService employeeService;
    // Where the results are printed.
    private EmployeeView employeeView;

    // Creates the controller: the service shares the repository and gets the salary
    // comparator as its strategy.
    public EmployeeController() {
        employeeRepository = new EmployeeRepository();
        employeeService = new EmployeeService(employeeRepository, new SalaryComparator());
        employeeView = new EmployeeView();
    }

    // Pre-check for Update, Remove and Search: they are meaningless on an empty list, so
    // main calls this before asking anything.
    public void checkNotEmpty() throws Exception {
        // nothing stored yet
        if (employeeRepository.isEmpty()) {
            throw new Exception(Message.LIST_EMPTY);
        }
    }

    // Pre-check for Add, called right after the Id is typed, so a duplicate is reported
    // before the nine other questions.
    public void checkNewId(EmployeeRequestDTO requestDTO) throws Exception {
        // the brief: the Id must be unique
        if (employeeRepository.isExistEmployee(requestDTO)) {
            throw new Exception(String.format(Message.ID_EXIST, requestDTO.getId()));
        }
    }

    // Option 1 (addEmployee): stores a new employee.
    public void addEmployee(EmployeeRequestDTO requestDTO) throws Exception {
        checkNewId(requestDTO);
        employeeRepository.addEmployee(requestDTO);
        employeeView.showMessage(String.format(Message.ADD_SUCCESS, requestDTO.getId()));
    }

    // Pre-check for Update: finds the employee, so main can show the old values in
    // brackets.
    public EmployeeResponseDTO findEmployee(EmployeeRequestDTO requestDTO) throws Exception {
        EmployeeResponseDTO employee = employeeRepository.findEmployee(requestDTO);
        // the brief: tell the user when no match is found
        if (employee == null) {
            throw new Exception(String.format(Message.ID_NOT_FOUND, requestDTO.getId()));
        }
        return employee;
    }

    // Option 2 (updateEmployee): stores the new values and shows the result.
    public void updateEmployee(EmployeeRequestDTO requestDTO) throws Exception {
        EmployeeResponseDTO updated = employeeRepository.updateEmployee(requestDTO);
        // the Id vanished between findEmployee and now
        if (updated == null) {
            throw new Exception(String.format(Message.ID_NOT_FOUND, requestDTO.getId()));
        }
        employeeView.showMessage(String.format(Message.UPDATE_SUCCESS, updated.getId()));
        employeeView.showMessage(String.format(Message.UPDATED_DETAIL, updated));
    }

    // Option 3 (removeEmployee): deletes the employee with the typed Id.
    public void removeEmployee(EmployeeRequestDTO requestDTO) throws Exception {
        // the brief: tell the user when no match is found
        if (!employeeRepository.removeEmployee(requestDTO)) {
            throw new Exception(String.format(Message.ID_NOT_FOUND, requestDTO.getId()));
        }
        employeeView.showMessage(String.format(Message.REMOVE_SUCCESS, requestDTO.getId()));
    }

    // Option 4 (searchByName): shows the employees whose first or last name contains the
    // text.
    public void searchByName(EmployeeRequestDTO requestDTO) {
        ArrayList<EmployeeResponseDTO> result = employeeRepository.searchByName(requestDTO);
        // nobody matched
        if (result.isEmpty()) {
            employeeView.showMessage(String.format(Message.NO_MATCH, requestDTO.getKeyword()));
            return;
        }
        employeeView.setEmployees(result);
        employeeView.displaySearch();
    }

    // Option 5 (sortBySalary): shows every employee, lowest salary first.
    public void sortBySalary() throws Exception {
        checkNotEmpty();
        employeeView.setEmployees(employeeService.sortBySalary());
        employeeView.display();
    }
}
