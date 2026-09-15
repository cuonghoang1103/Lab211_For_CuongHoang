package view;

import constants.Constants;
import constants.Message;
import dto.PersonResponseDTO;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * VIEW: prints the persons, one block each, in the order it receives them.
 *
 * @author HE176322
 */
public class PersonView {

    // The persons to display, already sorted by the service.
    private PersonResponseDTO[] persons;
    // Turns a salary into text such as "500.0".
    private DecimalFormat salaryFormat = new DecimalFormat(Constants.SALARY_FORMAT,
            DecimalFormatSymbols.getInstance(Locale.US));

    // Creates the view; the persons arrive later through setPersons.
    public PersonView() {
    }

    // Receives the persons the next display() call will print.
    public void setPersons(PersonResponseDTO[] persons) {
        this.persons = persons;
    }

    // Prints every person, in the order received.
    public void display() {
        // one block per person, lowest salary first
        for (PersonResponseDTO person : persons) {
            displayPersonInfo(person);
        }
    }

    // The brief's displayPersonInfo: prints one person's properties, then an empty line
    // that separates it from the next block (the brief's screen).
    public void displayPersonInfo(PersonResponseDTO person) {
        System.out.println(Message.TITLE_PERSON);
        System.out.println(Message.LABEL_NAME + person.getName());
        System.out.println(Message.LABEL_ADDRESS + person.getAddress());
        System.out.println(Message.LABEL_SALARY + salaryFormat.format(person.getSalary()));
        System.out.println();
    }
}
