package repository;

import java.util.ArrayList;
import model.Student;

/**
 * REPOSITORY: holds the data of the program - the list of students - and only simple CRUD
 * on it. No rule, no print.
 *
 * @author HE176322
 */
public class StudentRepository implements IStudentRepository {

    // The "database": students in the order they were created.
    private ArrayList<Student> studentList;

    // Creates an empty store.
    public StudentRepository() {
        studentList = new ArrayList<>();
    }

    // Read: counts the stored students.
    @Override
    public int countStudents() {
        return studentList.size();
    }

    // Read: finds a student by id with a linear scan, ignoring case ("s001" and "S001" are
    // the same student); null when nobody has it.
    @Override
    public Student findById(String id) {
        // look at every student once
        for (Student student : studentList) {
            // same id, whatever the case
            if (student.getId().equalsIgnoreCase(id)) {
                return student;
            }
        }

        return null;
    }

    // Create: appends a new student at the end of the list.
    @Override
    public void addStudent(Student student) {
        studentList.add(student);
    }

    // Update: replaces the student that has the same id, keeping its position.
    @Override
    public boolean updateStudent(Student student) {
        // walk by index so the student keeps its place in the list
        for (int i = 0; i < studentList.size(); i++) {
            // found the old version: put the new one in its place
            if (studentList.get(i).getId().equalsIgnoreCase(student.getId())) {
                studentList.set(i, student);
                return true;
            }
        }

        return false;
    }

    // Delete: removes the student with this id.
    @Override
    public boolean deleteStudent(String id) {
        Student student = findById(id);

        // nobody has this id: nothing to remove
        if (student == null) {
            return false;
        }

        return studentList.remove(student);
    }

    // Read: returns a copy of the list, so a search that sorts its result can never reorder
    // the stored students.
    @Override
    public ArrayList<Student> findAll() {
        return new ArrayList<>(studentList);
    }
}
