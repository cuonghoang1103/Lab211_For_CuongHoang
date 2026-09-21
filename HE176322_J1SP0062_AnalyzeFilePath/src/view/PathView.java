package view;

import constants.Message;
import dto.PathResponseDTO;
import java.util.Arrays;

/**
 * VIEW: prints the five answers under the "Result Analysis" title. It receives the data
 * through its attribute (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class PathView {

    // The result to display, handed over by the controller.
    private PathResponseDTO responseDTO;

    // Creates the view; the result arrives later through setResponseDTO.
    public PathView() {
    }

    // Receives the result the next display() call will print.
    public void setResponseDTO(PathResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints the brief's result screen.
    public void display() {
        System.out.println(Message.TITLE_RESULT);
        System.out.println(String.format(Message.RESULT_DISK, responseDTO.getDisk()));
        System.out.println(String.format(Message.RESULT_EXTENSION, responseDTO.getExtension()));
        System.out.println(String.format(Message.RESULT_FILE_NAME, responseDTO.getFileName()));
        System.out.println(String.format(Message.RESULT_PATH, responseDTO.getPath()));
        System.out.println(String.format(Message.RESULT_FOLDERS,
                Arrays.toString(responseDTO.getFolderArray())));
    }
}
