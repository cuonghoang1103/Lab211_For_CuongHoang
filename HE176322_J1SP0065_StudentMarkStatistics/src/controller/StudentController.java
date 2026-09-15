package controller;

import dto.ReportResponseDTO;
import dto.StudentRequestDTO;
import java.util.ArrayList;
import service.StandardClassificationStrategy;
import service.StudentService;
import view.StudentView;

/**
 * CONTROLLER (and FACADE): takes the typed students from main, asks the service for the
 * report, and hands it to the view.
 *
 * @author HE176322
 */
public class StudentController {

    // Creates, classifies and counts the students.
    private StudentService studentService;
    // Prints the report.
    private StudentView studentView;

    // Creates the controller: the service gets the brief's four-band rule.
    public StudentController() {
        studentService = new StudentService(new StandardClassificationStrategy());
        studentView = new StudentView();
    }

    // Function 2: classifies the students and shows them with the statistics.
    public void classifyStudents(ArrayList<StudentRequestDTO> requestList) {
        ReportResponseDTO report = studentService.makeReport(requestList);
        studentView.setReport(report);
        studentView.display();
    }
}
