package repository;

import java.util.ArrayList;
import model.Student;

/**
 * The CRUD contract of the student store (Dependency Inversion).
 *
 * @author HE176322
 */
public interface IStudentRepository {

    // Counts the stored students.
    int countStudents();

    // Finds a student by id, ignoring case.
    Student findById(String id);

    // Stores a new student.
    void addStudent(Student student);

    // Replaces the stored student that has the same id.
    boolean updateStudent(Student student);

    // Removes the student with this id.
    boolean deleteStudent(String id);

    // Returns every student, in the order they were created.
    ArrayList<Student> findAll();
}
