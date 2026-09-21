package view;

import constants.Constants;
import constants.Message;
import dto.EmployeeResponseDTO;

/**
 * VIEW: the only place (with main) allowed to print results. It receives the data through
 * its attribute (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class EmployeeView {

    // The answer to print, handed over by the controller.
    private EmployeeResponseDTO responseDTO;

    // Receives the answer the next display() call will print.
    public void setResponseDTO(EmployeeResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // The brief's display(): prints what the controller set - a one-line result (with the
    // updated employee under it), the search table, or the list sorted by salary.
    public void display() {
        // a one-line result: "=> Employee E001 added successfully.", "=> No employee
        // matches "zz".", ...
        if (responseDTO.getMessage() != null) {
            System.out.println(responseDTO.getMessage());
        }

        // the employee just updated, indented, on the line under the message
        if (responseDTO.getDetail() != null) {
            System.out.println(String.format(Message.UPDATED_DETAIL, responseDTO.getDetail()));
        }

        // the search table of the brief: header, line, one row per matching employee
        if (responseDTO.getSearchRowList() != null) {
            System.out.println(String.format(Constants.SEARCH_ROW, Message.HEADER_ID,
                    Message.HEADER_FIRST_NAME, Message.HEADER_LAST_NAME,
                    Message.HEADER_SALARY, Message.HEADER_AGENCY));
            System.out.println(Message.SEARCH_LINE);

            // the rows, in the order the employees were added
            for (String row : responseDTO.getSearchRowList()) {
                System.out.println(row);
            }
        }

        // the sorted list of the brief: title, header, line, one row per employee
        if (responseDTO.getSortRowList() != null) {
            System.out.println(Message.TITLE_SORT);
            System.out.println(String.format(Constants.SORT_ROW, Message.HEADER_ID,
                    Message.HEADER_NAME, Message.HEADER_SALARY, Message.HEADER_AGENCY));
            System.out.println(Message.SORT_LINE);

            // the rows, lowest salary first
            for (String row : responseDTO.getSortRowList()) {
                System.out.println(row);
            }
        }
    }
}
