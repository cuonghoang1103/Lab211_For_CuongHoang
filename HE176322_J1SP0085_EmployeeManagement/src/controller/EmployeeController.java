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
 * CONTROLLER (and FACADE for main): the brief's EmployeeManager operations. Receives a
 * request DTO from main, asks the repository (CRUD, search) or the service (sort) to do the
 * work, and hands the answer to the view - one render per menu option. No Scanner, no
 * print, no model.
 *
 * @author HE176322
 */
public class EmployeeController {

    // Where the employees are stored (CRUD and search): Controller -> Repository -> Model.
    private EmployeeRepository employeeRepository;

    // Sorts the employees (Controller -> Service -> Repository); set up with the salary
    // order.
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

    // A check only (no render) for Update, Remove and Search: on an empty list they have
    // nothing to work on, so main calls this before asking anything.
    public void checkNotEmpty() throws Exception {
        // nothing stored yet: "=> The employee list is empty."
        if (employeeRepository.isEmpty()) {
            throw new Exception(Message.LIST_EMPTY);
        }
    }

    // A check only (no render) for Add, called right after the Id is typed (the brief: make
    // sure the Id is not already used), so a duplicate is reported before the nine other
    // questions.
    public void checkNewId(EmployeeRequestDTO requestDTO) throws Exception {
        // the brief: the Id must be unique
        if (employeeRepository.isExistEmployee(requestDTO.getId())) {
            throw new Exception(String.format(Message.ID_EXIST, requestDTO.getId()));
        }
    }

    // Option 1 (brief: + addEmployee()): stores the new employee, then the view prints
    // "=> Employee E001 added successfully." - once.
    public void addEmployee(EmployeeRequestDTO requestDTO) throws Exception {
        EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();

        // the Id is checked once more: nothing may store a duplicate
        checkNewId(requestDTO);
        employeeRepository.addEmployee(requestDTO);

        // stored: hand the answer to the view, then render it - once for the whole flow
        responseDTO.setMessage(String.format(Message.ADD_SUCCESS, requestDTO.getId()));
        employeeView.setResponseDTO(responseDTO);
        employeeView.display();
    }

    // A check only (no render) for Update: the typed Id must exist (the brief: "tell the
    // user when no match is found"); its current values are loaded into the request, so
    // main can show them in brackets.
    public void loadEmployee(EmployeeRequestDTO requestDTO) throws Exception {
        // no employee has this Id
        if (!employeeRepository.loadEmployee(requestDTO)) {
            throw new Exception(String.format(Message.ID_NOT_FOUND, requestDTO.getId()));
        }
    }

    // Option 2 (brief: + updateEmployee(id)): stores the new values, then the view prints
    // "=> Employee E002 updated successfully." and the employee on one line - once.
    public void updateEmployee(EmployeeRequestDTO requestDTO) throws Exception {
        EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();
        String detail = employeeRepository.updateEmployee(requestDTO);

        // the Id vanished between loadEmployee and now
        if (detail == null) {
            throw new Exception(String.format(Message.ID_NOT_FOUND, requestDTO.getId()));
        }

        // updated: hand the answer to the view, then render it - once for the whole flow
        responseDTO.setMessage(String.format(Message.UPDATE_SUCCESS, requestDTO.getId()));
        responseDTO.setDetail(detail);
        employeeView.setResponseDTO(responseDTO);
        employeeView.display();
    }

    // Option 3 (brief: + removeEmployee(id)): deletes the employee with the typed Id, then
    // the view prints "=> Employee E001 removed successfully." - once.
    public void removeEmployee(EmployeeRequestDTO requestDTO) throws Exception {
        EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();

        // the brief: tell the user when no match is found
        if (!employeeRepository.removeEmployee(requestDTO.getId())) {
            throw new Exception(String.format(Message.ID_NOT_FOUND, requestDTO.getId()));
        }

        // removed: hand the answer to the view, then render it - once for the whole flow
        responseDTO.setMessage(String.format(Message.REMOVE_SUCCESS, requestDTO.getId()));
        employeeView.setResponseDTO(responseDTO);
        employeeView.display();
    }

    // Option 4 (brief: + searchByName(name)): the view prints the table of the employees
    // whose first or last name contains the text, or the "no match" line - once.
    public void searchByName(EmployeeRequestDTO requestDTO) {
        EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();
        ArrayList<String> rowList = employeeRepository.searchByName(requestDTO.getKeyword());

        // nobody matched: one line instead of an empty table
        if (rowList.isEmpty()) {
            responseDTO.setMessage(String.format(Message.NO_MATCH, requestDTO.getKeyword()));
        } else {
            // at least one match: the table of the brief
            responseDTO.setSearchRowList(rowList);
        }

        // hand the answer to the view, then render it - once for the whole flow
        employeeView.setResponseDTO(responseDTO);
        employeeView.display();
    }

    // Option 5 (brief: + sortBySalary(), then + display()): the view prints every employee,
    // lowest salary first - once.
    public void sortBySalary() throws Exception {
        EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();

        // nothing stored yet: "=> The employee list is empty."
        checkNotEmpty();

        // the service sorts a copy of the list; hand the rows to the view, then render once
        responseDTO.setSortRowList(employeeService.sortBySalary());
        employeeView.setResponseDTO(responseDTO);
        employeeView.display();
    }
}
