package service;

import dto.StudentDTO;
import dto.StudentRequestDTO;
import dto.StudentResponseDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import model.Student;
import repository.StudentRepository;

/**
 * SERVICE and Strategy CONTEXT: keeps the typed students in the repository, sorts them
 * with the comparator it was given, and returns what the view shows. Called only by the
 * controller; no print, no keyboard.
 *
 * @author HE176322
 */
public class StudentService {

    // Keeps the students the service works on (Service -> Repository -> Model).
    private StudentRepository studentRepository;

    // The ordering rule (the Strategy); StudentComparator in this program.
    private Comparator<Student> studentComparator;

    // Creates the service with an empty repository and the ordering it must use.
    public StudentService(Comparator<Student> studentComparator) {
        studentRepository = new StudentRepository();
        this.studentComparator = studentComparator;
    }

    // Stores the typed students in the repository, sorts them with sortStudent and copies
    // them into rows for the view.
    public StudentResponseDTO getSortedStudents(StudentRequestDTO requestDTO) {
        // brief: sortStudent returns List<Student>, so the sorted list keeps that type
        List<Student> sortedList = null;
        StudentResponseDTO responseDTO = new StudentResponseDTO();
        ArrayList<StudentDTO> rowList = new ArrayList<>();

        // one model object per typed student, same order, kept by the repository
        for (StudentDTO studentDTO : requestDTO.getStudentList()) {
            studentRepository.addStudent(studentDTO);
        }

        // the brief's sortStudent works on the list the repository holds
        sortedList = sortStudent(studentRepository.getStudentList());

        // copy each sorted student into the row the view is allowed to see
        for (Student student : sortedList) {
            rowList.add(new StudentDTO(student.getName(), student.getClasses(),
                    student.getMark()));
        }

        // the rows go to the view inside the response
        responseDTO.setStudentList(rowList);
        return responseDTO;
    }

    // The brief's Function 1 method: sorts the list with Collections.sort and the
    // comparator (name from A to Z) and returns it.
    // brief: List<Student> sortStudent(List<Student> students)
    private List<Student> sortStudent(List<Student> studentList) {
        Collections.sort(studentList, studentComparator);
        return studentList;
    }
}
