package repository;

import java.util.ArrayList;
import model.Student;

/**
 * REPOSITORY: holds the list of students and does the simple CRUD on it.
 *
 * @author HE176322
 */
public class StudentRepository implements IStudentRepository {

    // The "database": students in the order they were created.
    private ArrayList<Student> students = new ArrayList<>();

    // Creates an empty store.
    public StudentRepository() {
    }

    // Counts the stored students.
    @Override
    public int countStudents() {
        return students.size();
    }

    // Finds a student by id with a linear scan, ignoring case ("s001" and "S001" are the
    // same student).
    @Override
    public Student findById(String id) {
        // look at every student once
        for (Student student : students) {
            // same id, whatever the case
            if (student.getId().equalsIgnoreCase(id)) {
                return student;
            }
        }
        return null;
    }

    // Appends a new student at the end of the list.
    @Override
    public void addStudent(Student student) {
        students.add(student);
    }

    // Replaces the student that has the same id, keeping its position.
    @Override
    public boolean updateStudent(Student student) {
        // walk by index so the student keeps its place in the list
        for (int i = 0; i < students.size(); i++) {
            // found the old version: put the new one in its place
            if (students.get(i).getId().equalsIgnoreCase(student.getId())) {
                students.set(i, student);
                return true;
            }
        }
        return false;
    }

    // Removes the student with this id.
    @Override
    public boolean deleteStudent(String id) {
        Student student = findById(id);
        // nobody has this id: nothing to remove
        if (student == null) {
            return false;
        }
        return students.remove(student);
    }

    // Returns a copy of the list, so a search that sorts its result can never reorder the
    // stored students.
    @Override
    public ArrayList<Student> findAll() {
        return new ArrayList<>(students);
    }
}
