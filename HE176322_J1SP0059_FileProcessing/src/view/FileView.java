package view;

import constants.Message;
import dto.PersonResponseDTO;
import dto.ReportResponseDTO;

/**
 * VIEW: the only place (with main) allowed to print results.
 *
 * @author HE176322
 */
public class FileView {

    // The report to display, handed over by the controller.
    private ReportResponseDTO report;

    // Receives the report the next display() call will print.
    public void setReport(ReportResponseDTO report) {
        this.report = report;
    }

    // Prints the brief's result screen: the title, the table, a blank line, then "Max:
    // ..." and "Min: ...".
    public void display() {
        System.out.println(Message.TITLE_RESULT);
        // nobody earns at least the money entered
        if (report == null || report.getPersons().isEmpty()) {
            System.out.println(Message.NO_PERSON);
            return;
        }
        System.out.println(Message.HEADER);
        // one line per person; toString() of the DTO is the formatted row
        for (PersonResponseDTO person : report.getPersons()) {
            System.out.println(person);
        }
        System.out.println();
        System.out.println(String.format(Message.LABEL_MAX, report.getMaxName()));
        System.out.println(String.format(Message.LABEL_MIN, report.getMinName()));
    }

    // Prints a one-line result such as "Copy done...".
    public void showMessage(String message) {
        System.out.println(message);
    }
}
