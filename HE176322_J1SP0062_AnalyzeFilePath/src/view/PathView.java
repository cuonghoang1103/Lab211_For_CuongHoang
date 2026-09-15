package view;

import constants.Message;
import dto.PathResponseDTO;
import java.util.Arrays;

/**
 * VIEW: prints the five answers under the "Result Analysis" title.
 *
 * @author HE176322
 */
public class PathView {

    // The result to display, handed over by the controller.
    private PathResponseDTO response;

    // Creates the view; the result arrives later through setResponse.
    public PathView() {
    }

    // Receives the result the next display() call will print.
    public void setResponse(PathResponseDTO response) {
        this.response = response;
    }

    // Prints the brief's result screen.
    public void display() {
        System.out.println(Message.TITLE_RESULT);
        System.out.println(Message.LABEL_DISK + response.getDisk());
        System.out.println(Message.LABEL_EXTENSION + response.getExtension());
        System.out.println(Message.LABEL_FILE_NAME + response.getFileName());
        System.out.println(Message.LABEL_PATH + response.getPath());
        System.out.println(Message.LABEL_FOLDERS + Arrays.toString(response.getFolders()));
    }
}
