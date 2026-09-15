package view;

import dto.FileResponseDTO;

/**
 * VIEW: prints file lists and one-line results.
 *
 * @author HE176322
 */
public class FileView {

    // The file names to display, handed over by the controller.
    private FileResponseDTO response;

    // Receives the names the next displayFileNames() call will print.
    public void setResponse(FileResponseDTO response) {
        this.response = response;
    }

    // Prints one name per line, then the summary line.
    public void displayFileNames(String summaryFormat) {
        // one line per file name
        for (String name : response.getFileNames()) {
            System.out.println(name);
        }
        System.out.println(String.format(summaryFormat, response.getFileNames().size()));
    }

    // Prints a one-line result such as "Write done".
    public void showMessage(String message) {
        System.out.println(message);
    }
}
