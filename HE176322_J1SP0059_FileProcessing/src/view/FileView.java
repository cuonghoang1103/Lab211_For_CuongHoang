package view;

import constants.Message;
import dto.FileResponseDTO;
import dto.PersonResponseDTO;

/**
 * VIEW: the only place (with main) allowed to print results. It receives the data through
 * its attribute (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class FileView {

    // The answer to print, handed over by the controller.
    private FileResponseDTO responseDTO;

    // Receives the answer the next display() will print.
    public void setResponseDTO(FileResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints what the controller set: the one line of "Copy text", or the result screen of
    // "Find person info".
    public void display() {
        // a one-line answer: "Copy done..."
        if (responseDTO.getMessage() != null) {
            System.out.println(responseDTO.getMessage());
        }

        // the result of "Find person info"
        if (responseDTO.getPersonList() != null) {
            displayResult();
        }
    }

    // Prints the brief's result screen: the title, the table, a blank line, then "Max:
    // ..." and "Min: ...".
    private void displayResult() {
        System.out.println(Message.TITLE_RESULT);

        // nobody earns at least the money entered
        if (responseDTO.getPersonList().isEmpty()) {
            System.out.println(Message.NO_PERSON);
            return;
        }

        System.out.println(Message.HEADER);

        // one line per person; toString() of the DTO is the formatted row
        for (PersonResponseDTO person : responseDTO.getPersonList()) {
            System.out.println(person);
        }

        System.out.println();
        System.out.println(String.format(Message.LABEL_MAX, responseDTO.getMaxName()));
        System.out.println(String.format(Message.LABEL_MIN, responseDTO.getMinName()));
    }
}
