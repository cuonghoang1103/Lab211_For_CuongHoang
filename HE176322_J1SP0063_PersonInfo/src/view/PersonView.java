package view;

import constants.Constants;
import constants.Message;
import dto.PersonDTO;
import dto.PersonResponseDTO;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * VIEW: prints the persons, one block each, in the order it receives them. It receives
 * the data through its attribute (the ResponseDTO), never through the parameters of
 * display().
 *
 * @author HE176322
 */
public class PersonView {

    // The persons to display, already sorted by the service.
    private PersonResponseDTO responseDTO;

    // Turns a salary into text such as "500.0".
    private DecimalFormat salaryFormat;

    // Creates the view; the persons arrive later through setResponseDTO.
    public PersonView() {
        salaryFormat = new DecimalFormat(Constants.SALARY_FORMAT,
                DecimalFormatSymbols.getInstance(Locale.US));
    }

    // Receives the persons the next display() call will print.
    public void setResponseDTO(PersonResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints every person, in the order received.
    public void display() {
        // one block per person, lowest salary first
        for (PersonDTO personDTO : responseDTO.getPersonArray()) {
            displayPersonInfo(personDTO);
        }
    }

    // The brief's displayPersonInfo: prints one person's properties, then an empty line
    // that separates it from the next block (the brief's screen). Private: only display()
    // calls it, with a row of the attribute.
    // brief: void displayPersonInfo(Person person)
    private void displayPersonInfo(PersonDTO personDTO) {
        System.out.println(Message.TITLE_PERSON);
        System.out.println(String.format(Message.RESULT_NAME, personDTO.getName()));
        System.out.println(String.format(Message.RESULT_ADDRESS, personDTO.getAddress()));
        System.out.println(String.format(Message.RESULT_SALARY,
                salaryFormat.format(personDTO.getSalary())));
        System.out.println();
    }
}
