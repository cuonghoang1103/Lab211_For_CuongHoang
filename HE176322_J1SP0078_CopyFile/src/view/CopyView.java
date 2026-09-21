package view;

import constants.Message;
import dto.CopyResponseDTO;

/**
 * VIEW: prints the result of the copy (box 5 of the brief). It receives the data through
 * its attribute (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class CopyView {

    // The result to display, handed over by the controller.
    private CopyResponseDTO responseDTO;

    // Receives the result the next display() call will print.
    public void setResponseDTO(CopyResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints box 5: "Copy is running...", the list of copied files between the brief's
    // two lines, then "Copy is finished...".
    public void display() {
        // the two lines above the list
        System.out.println(Message.COPY_RUNNING);
        System.out.println(Message.TITLE_FILE_NAME);

        // one line per copied file
        for (String fileName : responseDTO.getFileNameList()) {
            System.out.println(fileName);
        }

        // the last line of box 5
        System.out.println(Message.COPY_FINISHED);
    }
}
