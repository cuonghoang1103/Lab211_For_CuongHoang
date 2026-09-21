package view;

import constants.Message;
import dto.FileResponseDTO;

/**
 * VIEW: prints the content read from the file. It receives the data through its attribute
 * (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class FileView {

    // The content to display, handed over by the controller.
    private FileResponseDTO responseDTO;

    // Creates the view.
    public FileView() {
    }

    // Receives the result the next display() call will print.
    public void setResponseDTO(FileResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints the brief's last screen: the content, then the success line.
    public void display() {
        System.out.println(responseDTO.getContent());
        System.out.println(Message.READ_SUCCESS);
    }
}
