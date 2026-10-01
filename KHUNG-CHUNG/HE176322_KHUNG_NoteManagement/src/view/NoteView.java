package view;

import constants.Constants;
import constants.Message;
import dto.NoteResponseDTO;

/**
 * VIEW: the only place (with main) allowed to print results. It receives the data through
 * its attribute (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class NoteView {

    // The answer to print, handed over by the controller.
    private NoteResponseDTO responseDTO;

    // Receives the answer the next display() call will print.
    public void setResponseDTO(NoteResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints what the controller set: the line of an add or a delete, or the note table.
    public void display() {
        // options 1 and 2: only the line of the result was set
        if (responseDTO.getRowList() == null) {
            System.out.println(responseDTO.getMessage());
            return;
        }

        // option 3: the title of the table comes first, even when there is no note
        System.out.println(Message.TITLE_NOTE);

        // nothing added yet (or everything deleted)
        if (responseDTO.getRowList().isEmpty()) {
            System.out.println(Message.NO_NOTE);
            return;
        }

        // the header, with the same column widths as the rows
        System.out.println(String.format(Constants.HEADER_FORMAT, Message.LABEL_ID,
                Message.LABEL_CONTENT));

        // one line per note
        for (String row : responseDTO.getRowList()) {
            System.out.println(row);
        }
    }
}
