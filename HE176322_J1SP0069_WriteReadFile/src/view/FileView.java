package view;

import constants.Message;
import dto.FileResponseDTO;

/**
 * VIEW: prints the content read from the file.
 *
 * @author HE176322
 */
public class FileView {

    // The content to display, handed over by the controller.
    private FileResponseDTO response;

    // Creates the view.
    public FileView() {
    }

    // Receives the result the next display() call will print.
    public void setResponse(FileResponseDTO response) {
        this.response = response;
    }

    // Prints the brief's last screen: the content, then the success line.
    public void display() {
        System.out.println(response.getContent());
        System.out.println(Message.READ_SUCCESS);
    }
}
