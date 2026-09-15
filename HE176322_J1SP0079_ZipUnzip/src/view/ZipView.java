package view;

import constants.Message;
import dto.ZipResponseDTO;

/**
 * VIEW: prints the result screen, the same for zipping and unzipping.
 *
 * @author HE176322
 */
public class ZipView {

    // The result to display, handed over by the controller.
    private ZipResponseDTO response;

    // Receives the result the next display() call will print.
    public void setResponse(ZipResponseDTO response) {
        this.response = response;
    }

    // Prints the brief's result screen: the files then "Successfully", or the reason then
    // "Failed".
    public void display() {
        System.out.println(Message.TITLE_RESULT);
        // a failed job shows why, not a half list of files
        if (!response.isSuccess()) {
            System.out.println(response.getError());
            System.out.println(Message.FAILED);
            return;
        }
        // one line per zipped/unzipped file
        for (String name : response.getFileNames()) {
            System.out.println(String.format(Message.FILE_NAME, name));
        }
        System.out.println(Message.SUCCESS);
    }
}
