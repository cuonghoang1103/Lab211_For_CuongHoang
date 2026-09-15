package view;

import constants.Message;
import dto.CopyResponseDTO;

/**
 * VIEW: prints the result of the copy (box 5 of the brief) and one-line messages.
 *
 * @author HE176322
 */
public class CopyView {

    // The result to display, handed over by the controller.
    private CopyResponseDTO response;

    // Receives the result the next display() call will print.
    public void setResponse(CopyResponseDTO response) {
        this.response = response;
    }

    // Prints the list of copied files between the brief's two lines.
    public void display() {
        System.out.println(Message.TITLE_FILE_NAME);
        // one line per copied file
        for (String name : response.getFileNames()) {
            System.out.println(name);
        }
        System.out.println(Message.COPY_FINISHED);
    }

    // Prints a one-line message such as "Copy is running...".
    public void showMessage(String message) {
        System.out.println(message);
    }
}
