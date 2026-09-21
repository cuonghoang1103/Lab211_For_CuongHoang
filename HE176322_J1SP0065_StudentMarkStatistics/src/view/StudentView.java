package view;

import constants.Constants;
import constants.Message;
import dto.ReportResponseDTO;
import dto.StudentResponseDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * VIEW: prints Function 2 - one block per student, then the statistics. It receives the
 * data through its attribute (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class StudentView {

    // The report to print, handed over by the controller.
    private ReportResponseDTO responseDTO;

    // Receives the report the next display() will print.
    public void setResponseDTO(ReportResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints every student block, then the four statistics lines in the fixed order A, B,
    // C, D (a HashMap has no order of its own).
    public void display() {
        ArrayList<StudentResponseDTO> studentList = responseDTO.getStudentList();
        HashMap<String, Double> percentMap = responseDTO.getPercentMap();
        StudentResponseDTO studentDTO = null;

        // one block per student; the user counts from 1, the list from 0
        for (int i = 0; i < studentList.size(); i++) {
            studentDTO = studentList.get(i);
            System.out.println(String.format(Message.TITLE_STUDENT, i + 1));
            System.out.println(String.format(Message.LABEL_NAME, studentDTO.getName()));
            System.out.println(String.format(Message.LABEL_CLASSES, studentDTO.getClasses()));
            System.out.println(String.format(Message.LABEL_AVG,
                    formatOneDecimal(studentDTO.getAverage())));
            System.out.println(String.format(Message.LABEL_TYPE, studentDTO.getType()));
        }

        // then the title of the statistics block
        System.out.println(Message.TITLE_CLASSIFICATION);

        // the four types, always in the same order
        for (String type : Constants.TYPE_ARRAY) {
            System.out.println(String.format(Message.PERCENT_LINE, type,
                    formatOneDecimal(percentMap.get(type))));
        }
    }

    // Writes a number with one decimal and a POINT: Locale.US, because on a Vietnamese
    // machine "%.1f" would print 10,0.
    private String formatOneDecimal(double value) {
        return String.format(Locale.US, Constants.ONE_DECIMAL, value);
    }
}
