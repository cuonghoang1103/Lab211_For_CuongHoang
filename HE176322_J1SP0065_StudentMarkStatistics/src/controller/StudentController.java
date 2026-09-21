package controller;

import dto.ReportRequestDTO;
import dto.ReportResponseDTO;
import service.StandardClassificationStrategy;
import service.StudentService;
import view.StudentView;

/**
 * CONTROLLER (and FACADE): takes the typed students from main, asks the service for the
 * report, and hands it to the view once. No Scanner, no print, no model.
 *
 * @author HE176322
 */
public class StudentController {

    // Creates, keeps, classifies and counts the students (Controller -> Service ->
    // Repository -> Model).
    private StudentService studentService;

    // Prints the report.
    private StudentView studentView;

    // Creates the controller: the service gets the brief's four-band rule.
    public StudentController() {
        studentService = new StudentService(new StandardClassificationStrategy());
        studentView = new StudentView();
    }

    // Function 2 (the only workflow): the service classifies the students and counts the
    // types, the view shows them with the statistics ONCE.
    public void classifyStudents(ReportRequestDTO requestDTO) {
        ReportResponseDTO responseDTO = studentService.makeReport(requestDTO);

        // hand the report to the view, then render it - once for the whole flow
        studentView.setResponseDTO(responseDTO);
        studentView.display();
    }
}
