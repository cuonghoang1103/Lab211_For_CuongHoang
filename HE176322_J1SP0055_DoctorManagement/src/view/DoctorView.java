package view;

import constants.Constants;
import constants.Message;
import dto.DoctorResponseDTO;

/**
 * VIEW: the only place (with main) allowed to print results. It receives the data through
 * its attribute (the ResponseDTO, as in the Guide sample), never through the parameters of
 * display().
 *
 * @author HE176322
 */
public class DoctorView {

    // The answer to print, handed over by the controller.
    private DoctorResponseDTO responseDTO;

    // Receives the answer the next display() call will print.
    public void setResponseDTO(DoctorResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints what the controller set: the one-line result of add/update/delete, or the
    // search result.
    public void display() {
        // add, update and delete answer with one line
        if (responseDTO.getMessage() != null) {
            System.out.println(responseDTO.getMessage());
        }

        // a search answers with its result
        if (responseDTO.getDoctorMap() != null) {
            displayResult();
        }
    }

    // Prints the search result: a title, then either "No doctor found." or a header and
    // one line per doctor.
    private void displayResult() {
        System.out.println(Message.TITLE_RESULT);

        // nobody matched the search text
        if (responseDTO.getDoctorMap().isEmpty()) {
            System.out.println(Message.NOT_FOUND);
        } else {
            // the header, with the same column widths as the rows
            System.out.println(String.format(Constants.HEADER_FORMAT, Message.LABEL_CODE,
                    Message.LABEL_NAME, Message.LABEL_SPECIALIZATION,
                    Message.LABEL_AVAILABILITY));

            // one line per doctor; Doctor.toString() already padded it
            for (String row : responseDTO.getDoctorMap().values()) {
                System.out.println(row);
            }
        }
    }
}
