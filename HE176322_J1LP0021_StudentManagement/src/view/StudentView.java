package view;

import constants.Constants;
import constants.Message;
import dto.ReportResponseDTO;
import dto.StudentResponseDTO;
import java.util.ArrayList;

/**
 * VIEW: the only place (with main) allowed to print results.
 *
 * @author HE176322
 */
public class StudentView {

    // Result of Find and Sort.
    private ArrayList<StudentResponseDTO> studentList;
    // The student found by id in Update/Delete.
    private StudentResponseDTO student;
    // Lines of the report.
    private ArrayList<ReportResponseDTO> reportList;

    // Receives the Find and Sort result.
    public void setStudentList(ArrayList<StudentResponseDTO> studentList) {
        this.studentList = studentList;
    }

    // Receives the student found by id.
    public void setStudent(StudentResponseDTO student) {
        this.student = student;
    }

    // Receives the report lines.
    public void setReportList(ArrayList<ReportResponseDTO> reportList) {
        this.reportList = reportList;
    }

    // Prints the brief's Find and Sort columns: student name, semester and course name -
    // or "No student found.".
    public void displaySearchResult() {
        // nobody matched the text
        if (studentList == null || studentList.isEmpty()) {
            System.out.println(Message.NOT_FOUND);
            return;
        }
        System.out.println(String.format(Constants.SEARCH_HEADER_FORMAT,
                Message.LABEL_NAME, Message.LABEL_SEMESTER, Message.LABEL_COURSE));
        // one line per student, in the sorted order
        for (StudentResponseDTO row : studentList) {
            System.out.println(String.format(Constants.SEARCH_ROW_FORMAT,
                    row.getStudentName(), row.getSemester(), row.getCourseName()));
        }
    }

    // Prints the header and the one student found by id.
    public void displayStudent() {
        System.out.println(String.format(Constants.STUDENT_HEADER_FORMAT,
                Message.LABEL_ID, Message.LABEL_NAME, Message.LABEL_SEMESTER,
                Message.LABEL_COURSE));
        System.out.println(student);
    }

    // Prints one report line per (name, course).
    public void displayReport() {
        // toString() of the DTO is the brief's "name | course | total"
        for (ReportResponseDTO line : reportList) {
            System.out.println(line);
        }
    }

    // Prints a one-line result such as "Student [S001] has been added.".
    public void showMessage(String message) {
        System.out.println(message);
    }
}
