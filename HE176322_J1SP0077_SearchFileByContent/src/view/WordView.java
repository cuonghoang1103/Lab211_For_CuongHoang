package view;

import constants.Message;
import dto.WordResponseDTO;

/**
 * VIEW: prints the count of option 1 and the file list of option 2. It receives the data
 * through its attribute (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class WordView {

    // The result to display, handed over by the controller.
    private WordResponseDTO responseDTO;

    // Receives the result the next display() call will print.
    public void setResponseDTO(WordResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints what the controller set: "Bout: n" (option 1), or the file names (option 2).
    public void display() {
        // option 1: the brief's own label "Bout: n"
        if (responseDTO.getMessage() != null) {
            System.out.println(responseDTO.getMessage());
        }

        // option 2: the title, then one file name per line
        if (responseDTO.getFileNameList() != null) {
            displayFileNameList();
        }
    }

    // Prints the title, then one file name per line.
    private void displayFileNameList() {
        // the brief's title above the names
        System.out.println(Message.TITLE_FILE_NAME);

        // an empty list under a title would look like a crash: say so
        if (responseDTO.getFileNameList().isEmpty()) {
            System.out.println(Message.NO_FILE_FOUND);
            return;
        }

        // one line per file
        for (String fileName : responseDTO.getFileNameList()) {
            System.out.println(fileName);
        }
    }
}
