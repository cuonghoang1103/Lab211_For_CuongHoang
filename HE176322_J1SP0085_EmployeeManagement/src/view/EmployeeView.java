package view;

import constants.Constants;
import constants.Message;
import dto.EmployeeResponseDTO;
import java.util.ArrayList;

/**
 * VIEW: the only place (with main) allowed to print results.
 *
 * @author HE176322
 */
public class EmployeeView {

    // The rows to display, handed over by the controller.
    private ArrayList<EmployeeResponseDTO> employees;

    // Receives the rows the next display call will print.
    public void setEmployees(ArrayList<EmployeeResponseDTO> employees) {
        this.employees = employees;
    }

    // The brief's display(): prints the list sorted by salary - title, header, line, one
    // row per employee.
    public void display() {
        System.out.println(Message.TITLE_SORT);
        System.out.println(String.format(Constants.SORT_ROW, Message.HEADER_ID,
                Message.HEADER_NAME, Message.HEADER_SALARY, Message.HEADER_AGENCY));
        System.out.println(Message.SORT_LINE);
        // one row per employee, in the order the service sorted them
        for (EmployeeResponseDTO employee : employees) {
            System.out.println(String.format(Constants.SORT_ROW, employee.getId(),
                    employee.getFullName(), employee.getSalaryText(),
                    employee.getAgency()));
        }
    }

    // Prints the search result table of the brief.
    public void displaySearch() {
        System.out.println(String.format(Constants.SEARCH_ROW, Message.HEADER_ID,
                Message.HEADER_FIRST_NAME, Message.HEADER_LAST_NAME,
                Message.HEADER_SALARY, Message.HEADER_AGENCY));
        System.out.println(Message.SEARCH_LINE);
        // one row per matching employee
        for (EmployeeResponseDTO employee : employees) {
            System.out.println(String.format(Constants.SEARCH_ROW, employee.getId(),
                    employee.getFirstName(), employee.getLastName(),
                    employee.getSalaryText(), employee.getAgency()));
        }
    }

    // Prints a one-line result such as "=> Employee E001 added successfully.".
    public void showMessage(String message) {
        System.out.println(message);
    }
}
