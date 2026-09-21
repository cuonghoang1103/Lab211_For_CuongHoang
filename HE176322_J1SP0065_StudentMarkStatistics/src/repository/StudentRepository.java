package repository;

import java.util.ArrayList;
import model.Student;

/**
 * REPOSITORY: holds the data of the program - the students entered - and only simple
 * CRUD on it. No average, no statistics, no print.
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

    // Create: keeps one student (built by the brief's createStudent) at the end of the list.
    public void addStudent(Student student) {
        studentList.add(student);
    }

    // Read: returns every student stored.
    public ArrayList<Student> getStudentList() {
        return studentList;
    }
}
