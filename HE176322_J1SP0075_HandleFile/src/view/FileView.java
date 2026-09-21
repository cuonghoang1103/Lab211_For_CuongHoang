package view;

import dto.FileResponseDTO;

/**
 * VIEW: prints file lists and one-line results. It receives the data through its attribute
 * (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class FileView {

    // The result to display, handed over by the controller.
    private FileResponseDTO responseDTO;

    // Receives the result the next display() call will print.
    public void setResponseDTO(FileResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints what the controller set: the file names (options 2 and 3), then the one-line
    // result.
    public void display() {
        // options 2 and 3: the file names come first
        if (responseDTO.getFileNameList() != null) {
            displayFileNameList();
        }

        // "Path to file", "Result 2 file!", "Write done", "Total:3"...
        if (responseDTO.getMessage() != null) {
            System.out.println(responseDTO.getMessage());
        }
    }

    // Prints one file name per line.
    private void displayFileNameList() {
        // one line per file name
        for (String fileName : responseDTO.getFileNameList()) {
            System.out.println(fileName);
        }
    }
}
