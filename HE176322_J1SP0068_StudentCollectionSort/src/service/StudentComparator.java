package service;

import java.util.Comparator;
import model.Student;

/**
 * CONCRETE STRATEGY: orders students by name from A to Z - the brief's "Student
 * Comparator class that implements Comparator interface, overrides the compare method".
 *
 * @author HE176322
 */
public class StudentComparator implements Comparator<Student> {

    // Creates the comparator.
    public StudentComparator() {
    }

    // Compares two students by name, ignoring upper/lower case, so "an" and "An" sit
    // together instead of all capitals coming first.
    @Override
    public int compare(Student first, Student second) {
        return first.getName().compareToIgnoreCase(second.getName());
    }
}
