package view;

import constants.Constants;
import constants.Message;
import dto.StudentResponseDTO;

/**
 * VIEW: the only place (with main) allowed to print results. It receives the data through
 * its attribute (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class StudentView {

    // The answer to print, handed over by the controller.
    private StudentResponseDTO responseDTO;

    // Receives the answer the next display() call will print.
    public void setResponseDTO(StudentResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints what the controller set: a one-line result, the lines of Create, the table of
    // Find and Sort, or the lines of the report.
    public void display() {
        // a one-line result: "Student [S01] has been updated.", "No student found."...
        if (responseDTO.getMessage() != null) {
            System.out.println(responseDTO.getMessage());
        }

        // Create: one "Student [id] has been added." per stored student
        if (responseDTO.getMessageList() != null) {
            // one line each, in the typed order
            for (String message : responseDTO.getMessageList()) {
                System.out.println(message);
            }
        }

        // Find and Sort: the brief's columns - student name, semester and course name
        if (responseDTO.getSearchRowList() != null) {
            System.out.println(String.format(Constants.SEARCH_HEADER_FORMAT,
                    Message.LABEL_NAME, Message.LABEL_SEMESTER, Message.LABEL_COURSE));

            // one row per student, in the sorted order (text of Student.toString())
            for (String row : responseDTO.getSearchRowList()) {
                System.out.println(row);
            }
        }

        // Report: the brief's "Nguyen Van A | Java | 2", one line per name and course
        if (responseDTO.getReportRowList() != null) {
            // one line each, sorted by name then course (text of ReportItem.toString())
            for (String row : responseDTO.getReportRowList()) {
                System.out.println(row);
            }
        }
    }
}
