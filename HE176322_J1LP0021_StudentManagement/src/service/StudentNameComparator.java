package service;

import java.util.Comparator;
import model.Student;

/**
 * CONCRETE STRATEGY: orders students by name - the brief's Guidelines "Should
 * Collections.sort() and overwrite compare() method in Comparator interface".
 *
 * @author HE176322
 */
public class StudentNameComparator implements Comparator<Student> {

    // Creates the comparator.
    public StudentNameComparator() {
    }

    // Compares by name ignoring case (so "anh" does not land after "Binh"), then by id
    // when two students share a name (the brief's own example has "Nguyen Van A" twice),
    // so the order is the same on every run.
    @Override
    public int compare(Student first, Student second) {
        int byName = first.getStudentName().compareToIgnoreCase(second.getStudentName());
        // different names decide the order
        if (byName != 0) {
            return byName;
        }
        return first.getId().compareToIgnoreCase(second.getId());
    }
}
