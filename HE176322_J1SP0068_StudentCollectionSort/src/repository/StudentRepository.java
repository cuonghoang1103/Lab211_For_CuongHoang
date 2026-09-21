package repository;

import dto.StudentDTO;
import java.util.ArrayList;
import model.Student;

/**
 * REPOSITORY: holds the data of the program - the students entered - and only simple
 * CRUD on it. No sorting, no print.
 *
 * @author HE176322
 */
public class StudentRepository {

    // Every student entered, in the order they were typed (the model).
    private ArrayList<Student> studentList;

    // Creates an empty store.
    public StudentRepository() {
        studentList = new ArrayList<>();
    }

    // Create: turns one typed row into a Student (the model) and stores it.
    public void addStudent(StudentDTO studentDTO) {
        Student student = new Student(studentDTO.getName(), studentDTO.getClasses(),
                studentDTO.getMark());

        // keep it at the end of the list
        studentList.add(student);
    }

    // Read: returns every student stored.
    public ArrayList<Student> getStudentList() {
        return studentList;
    }
}
