package view;

import constants.Constants;
import constants.Message;
import dto.ReportResponseDTO;
import dto.StudentResponseDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * VIEW: prints Function 2 - one block per student, then the statistics.
 *
 * @author HE176322
 */
public class StudentView {

    // The report to display, handed over by the controller.
    private ReportResponseDTO report;

    // Receives the report the next display() call will print.
    public void setReport(ReportResponseDTO report) {
        this.report = report;
    }

    // Prints every student block, then the four statistics lines in the fixed order A, B,
    // C, D (a HashMap has no order of its own).
    public void display() {
        ArrayList<StudentResponseDTO> students = report.getStudentList();
        // one block per student; the user counts from 1, the list from 0
        for (int i = 0; i < students.size(); i++) {
            StudentResponseDTO student = students.get(i);
            System.out.println(String.format(Message.TITLE_STUDENT, i + 1));
            System.out.println(Message.LABEL_NAME + student.getName());
            System.out.println(Message.LABEL_CLASSES + student.getClasses());
            System.out.println(Message.LABEL_AVG + oneDecimal(student.getAverage()));
            System.out.println(Message.LABEL_TYPE + student.getType());
        }
        System.out.println(Message.TITLE_CLASSIFICATION);
        HashMap<String, Double> percentMap = report.getPercentMap();
        // the four types always in the same order
        for (String type : Constants.TYPES) {
            System.out.println(String.format(Message.PERCENT_LINE, type,
                    oneDecimal(percentMap.get(type))));
        }
    }

    // Writes a number with one decimal and a POINT: Locale.US, because on a Vietnamese
    // machine "%.1f" would print 10,0.
    private String oneDecimal(double value) {
        return String.format(Locale.US, Constants.ONE_DECIMAL, value);
    }
}
