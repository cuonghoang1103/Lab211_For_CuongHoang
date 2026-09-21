package view;

import constants.Message;
import dto.ZipResponseDTO;

/**
 * VIEW: prints the result screen, the same for zipping and unzipping. It receives the data
 * through its attribute (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class ZipView {

    // The result to print, handed over by the controller.
    private ZipResponseDTO responseDTO;

    // Receives the result the next display() call will print.
    public void setResponseDTO(ZipResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints the brief's result screen: the files then "Successfully", or the reason then
    // "Failed".
    public void display() {
        System.out.println(Message.TITLE_RESULT);

        // a failed job shows why, not a half list of files
        if (!responseDTO.isSuccess()) {
            System.out.println(responseDTO.getError());
            System.out.println(Message.FAILED);
            return;
        }

        // one line per zipped/unzipped file
        for (String name : responseDTO.getFileNameList()) {
            System.out.println(String.format(Message.FILE_NAME, name));
        }

        System.out.println(Message.SUCCESS);
    }
}
