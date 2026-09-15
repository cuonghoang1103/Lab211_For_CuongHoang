package view;

import constants.Message;
import dto.WordResponseDTO;

/**
 * VIEW: prints the count of option 1 and the file list of option 2.
 *
 * @author HE176322
 */
public class WordView {

    // The result to display, handed over by the controller.
    private WordResponseDTO response;

    // Receives the result the next display call will print.
    public void setResponse(WordResponseDTO response) {
        this.response = response;
    }

    // Option 1: prints "Bout: n" (the brief's own label).
    public void displayCount() {
        System.out.println(String.format(Message.COUNT_RESULT, response.getCount()));
    }

    // Option 2: prints the title, then one file name per line.
    public void displayFileNames() {
        System.out.println(Message.TITLE_FILE_NAME);
        // an empty list under a title would look like a crash: say so
        if (response.getFileNames().isEmpty()) {
            System.out.println(Message.NO_FILE_FOUND);
            return;
        }
        // one line per file
        for (String name : response.getFileNames()) {
            System.out.println(name);
        }
    }
}
