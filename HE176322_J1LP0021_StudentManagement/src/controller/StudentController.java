package controller;

import constants.Constants;
import constants.Message;
import dto.StudentRequestDTO;
import dto.StudentResponseDTO;
import java.util.ArrayList;
import repository.StudentRepository;
import service.StudentNameComparator;
import service.StudentService;
import view.StudentView;

/**
 * CONTROLLER (and Facade): receives a request DTO from main, asks the service to do the
 * work, and hands the answer to the view - one render per menu option. No Scanner, no
 * print, no model.
 *
 * @author HE176322
 */
public class StudentController {

    // Business rules, search, sort and report (Controller -> Service -> Repository ->
    // Model).
    private StudentService studentService;

    // Prints the answer of each menu option.
    private StudentView studentView;

    // Creates the controller: an ArrayList store and "sort by name".
    public StudentController() {
        studentService = new StudentService(new StudentRepository(),
                new StudentNameComparator());
        studentView = new StudentView();
    }

    // Option 1, read only (no render, nothing changed): how many students the list already
    // holds, so main counts the whole list for the brief's "at least 10 students".
    public int countStudents() {
        return studentService.countStudents();
    }

    // Option 1, check only (no render, nothing stored): throws the reason when the student
    // main is typing breaks a rule, so main keeps only good students.
    public void checkStudent(StudentRequestDTO requestDTO) throws Exception {
        studentService.checkStudent(requestDTO);
    }

    // Option 1: stores every student main kept, then the view prints "Student [id] has
    // been added." for each one - once.
    public void createStudents(StudentRequestDTO requestDTO) throws Exception {
        StudentResponseDTO responseDTO = new StudentResponseDTO();

        // the service stores the students and gives one line per student
        responseDTO.setMessageList(studentService.createStudents(requestDTO));

        // hand the answer to the view, then render it - once for the whole flow
        studentView.setResponseDTO(responseDTO);
        studentView.display();
    }

    // Option 2: find by (part of) name, sort by name, then the view prints the table - or
    // "No student found." - once.
    public void findAndSort(StudentRequestDTO requestDTO) throws Exception {
        StudentResponseDTO responseDTO = new StudentResponseDTO();
        ArrayList<String> rowList = studentService.findAndSort(requestDTO);

        // nobody matched the text: say so instead of a bare header
        if (rowList.isEmpty()) {
            responseDTO.setMessage(Message.NOT_FOUND);
        } else {
            // at least one match: the header and one row per student
            responseDTO.setSearchRowList(rowList);
        }

        // hand the answer to the view, then render it - once for the whole flow
        studentView.setResponseDTO(responseDTO);
        studentView.display();
    }

    // Option 3, check only (no render, nothing changed): throws the reason when no student
    // has the id main read - the brief asks U or D only after finding the student.
    public void checkExistStudent(StudentRequestDTO requestDTO) throws Exception {
        studentService.checkExistStudent(requestDTO);
    }

    // Option 3: updates or deletes the student, as the user answered U or D, then the view
    // prints "Student [id] has been updated." or "... deleted." - once.
    public void updateOrDeleteStudent(StudentRequestDTO requestDTO) throws Exception {
        StudentResponseDTO responseDTO = new StudentResponseDTO();

        // D: the student is removed
        if (Constants.DELETE.equals(requestDTO.getOption())) {
            responseDTO.setMessage(studentService.deleteStudent(requestDTO));
        } else {
            // U: the fields the user typed are changed; blank ones keep the old value
            responseDTO.setMessage(studentService.updateStudent(requestDTO));
        }

        // hand the answer to the view, then render it - once for the whole flow
        studentView.setResponseDTO(responseDTO);
        studentView.display();
    }

    // Option 4: the service builds the report lines, the view prints them - once.
    public void report() throws Exception {
        StudentResponseDTO responseDTO = new StudentResponseDTO();

        // one "name | course | total" line per group
        responseDTO.setReportRowList(studentService.report());

        // hand the answer to the view, then render it - once for the whole flow
        studentView.setResponseDTO(responseDTO);
        studentView.display();
    }
}
