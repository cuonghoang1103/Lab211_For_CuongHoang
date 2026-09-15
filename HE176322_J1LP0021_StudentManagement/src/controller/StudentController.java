package controller;

import constants.Constants;
import constants.Message;
import dto.ReportResponseDTO;
import dto.StudentRequestDTO;
import dto.StudentResponseDTO;
import java.util.ArrayList;
import repository.StudentRepository;
import service.StudentNameComparator;
import service.StudentService;
import view.StudentView;

/**
 * CONTROLLER (and Facade): receives a request DTO from main, asks the service to do the
 * work, and hands the result to the view.
 *
 * @author HE176322
 */
public class StudentController {

    // Business rules, search, sort and report.
    private StudentService studentService;
    // Prints every result.
    private StudentView studentView;

    // Creates the controller: an ArrayList store and "sort by name".
    public StudentController() {
        studentService = new StudentService(new StudentRepository(),
                new StudentNameComparator());
        studentView = new StudentView();
    }

    // Function 1: adds one student and shows "Student [id] has been added.".
    public void createStudent(StudentRequestDTO requestDTO) throws Exception {
        StudentResponseDTO added = studentService.createStudent(requestDTO);
        studentView.showMessage(String.format(Message.ADD_SUCCESS, added.getId()));
    }

    // Tells whether the brief's minimum of 10 students is reached; when it is not, shows
    // how far off it is (a loop that will not let the user leave must say why).
    public boolean checkEnoughStudents() {
        int count = studentService.countStudents();
        // fewer than 10: say so, the loop asks for another student
        if (count < Constants.MIN_STUDENTS) {
            studentView.showMessage(String.format(Message.NEED_MORE,
                    Constants.MIN_STUDENTS, count));
            return false;
        }
        return true;
    }

    // Function 2: find by (part of) name, sort by name, then display.
    public void findAndSort(StudentRequestDTO requestDTO) throws Exception {
        ArrayList<StudentResponseDTO> result = studentService.findAndSort(requestDTO);
        studentView.setStudentList(result);
        studentView.displaySearchResult();
    }

    // Function 3, first step: finds the student by id and shows it, so the user sees WHO
    // will be updated or deleted.
    public StudentResponseDTO findStudent(StudentRequestDTO requestDTO) throws Exception {
        StudentResponseDTO found = studentService.findStudent(requestDTO);
        studentView.setStudent(found);
        studentView.displayStudent();
        return found;
    }

    // Function 3, choice U: updates the student.
    public void updateStudent(StudentRequestDTO requestDTO) throws Exception {
        StudentResponseDTO updated = studentService.updateStudent(requestDTO);
        studentView.showMessage(String.format(Message.UPDATE_SUCCESS, updated.getId()));
    }

    // Function 3, choice D: deletes the student.
    public void deleteStudent(StudentRequestDTO requestDTO) throws Exception {
        StudentResponseDTO deleted = studentService.deleteStudent(requestDTO);
        studentView.showMessage(String.format(Message.DELETE_SUCCESS, deleted.getId()));
    }

    // Function 4: builds the report and displays it.
    public void report() throws Exception {
        ArrayList<ReportResponseDTO> lines = studentService.report();
        studentView.setReportList(lines);
        studentView.displayReport();
    }
}
