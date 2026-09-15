package service;

import dto.StudentRequestDTO;
import dto.StudentResponseDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import model.Student;

/**
 * SERVICE and Strategy CONTEXT: turns the typed students into models, sorts them with the
 * comparator it was given, and returns what the view shows.
 *
 * @author HE176322
 */
public class StudentService {

    // The ordering rule (the Strategy); StudentComparator in this program.
    private Comparator<Student> studentComparator;

    // Creates the service with the ordering it must use.
    public StudentService(Comparator<Student> studentComparator) {
        this.studentComparator = studentComparator;
    }

    // Builds the students from the requests, sorts them with sortStudent and copies them
    // into response DTOs for the view.
    public ArrayList<StudentResponseDTO> getSortedStudents(
            ArrayList<StudentRequestDTO> requests) {
        ArrayList<Student> students = new ArrayList<>();
        // one model object per typed student, same order
        for (StudentRequestDTO request : requests) {
            students.add(new Student(request.getName(), request.getClasses(),
                    request.getMark()));
        }
        // brief: sortStudent returns List<Student>
        List<Student> sorted = sortStudent(students);
        ArrayList<StudentResponseDTO> responses = new ArrayList<>();
        // copy each sorted student into the DTO the view is allowed to see
        for (Student student : sorted) {
            responses.add(new StudentResponseDTO(student.getName(),
                    student.getClasses(), student.getMark()));
        }
        return responses;
    }

    // The brief's Function 1 method: sorts the list with Collections.sort and the
    // comparator.
    // brief: List<Student> sortStudent(List<Student> students)
    private List<Student> sortStudent(List<Student> students) {
        Collections.sort(students, studentComparator);
        return students;
    }
}
