package controller;

import dto.StudentRequestDTO;
import dto.StudentResponseDTO;
import service.StudentComparator;
import service.StudentService;
import view.StudentView;

/**
 * CONTROLLER (Facade): receives the typed students from main, lets the service store and
 * sort them, and hands the result to the view once. No Scanner, no print, no model.
 *
 * @author HE176322
 */
public class StudentController {

    // Stores and sorts the students, configured with the name comparator
    // (Controller -> Service -> Repository -> Model).
    private StudentService studentService;

    // Prints the students.
    private StudentView studentView;

    // Creates the controller: the service gets StudentComparator as its ordering.
    public StudentController() {
        studentService = new StudentService(new StudentComparator());
        studentView = new StudentView();
    }

    // Function 2 workflow: the service sorts by name, the view shows the list ONCE.
    public void displaySortedStudents(StudentRequestDTO requestDTO) {
        StudentResponseDTO responseDTO = studentService.getSortedStudents(requestDTO);

        // hand the sorted students to the view, then render them - once for the whole flow
        studentView.setResponseDTO(responseDTO);
        studentView.display();
    }
}
