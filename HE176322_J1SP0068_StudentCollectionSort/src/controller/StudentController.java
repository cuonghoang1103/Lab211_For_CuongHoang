package controller;

import dto.StudentRequestDTO;
import dto.StudentResponseDTO;
import java.util.ArrayList;
import service.StudentComparator;
import service.StudentService;
import view.StudentView;

/**
 * CONTROLLER (Facade): receives the typed students from main, asks the service to sort
 * them, and hands the result to the view.
 *
 * @author HE176322
 */
public class StudentController {

    // Sorts the students; configured with the name comparator.
    private StudentService studentService;
    // Prints the students.
    private StudentView studentView;

    // Creates the controller: the service gets StudentComparator as its ordering.
    public StudentController() {
        studentService = new StudentService(new StudentComparator());
        studentView = new StudentView();
    }

    // Function 2 workflow: sort by name, then display.
    public void displaySortedStudents(ArrayList<StudentRequestDTO> requests) {
        ArrayList<StudentResponseDTO> sorted = studentService.getSortedStudents(requests);
        studentView.display(sorted);
    }
}
