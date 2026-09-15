package service;

import java.util.Comparator;
import model.ReportItem;

/**
 * CONCRETE STRATEGY: orders report lines by student name, then by course, so all lines of
 * one student sit together like the brief's example.
 *
 * @author HE176322
 */
public class ReportComparator implements Comparator<ReportItem> {

    // Creates the comparator.
    public ReportComparator() {
    }

    // Compares by name ignoring case, then by course label.
    @Override
    public int compare(ReportItem first, ReportItem second) {
        int byName = first.getStudentName().compareToIgnoreCase(second.getStudentName());
        // different names decide the order
        if (byName != 0) {
            return byName;
        }
        return first.getCourse().getLabel().compareToIgnoreCase(
                second.getCourse().getLabel());
    }
}
