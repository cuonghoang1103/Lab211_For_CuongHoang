package view;

import constants.Message;
import dto.WordResponseDTO;

/**
 * VIEW: the only place (with main) allowed to print results. It receives the data through
 * its attribute (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class DictionaryView {

    // The answer to print, handed over by the controller.
    private WordResponseDTO responseDTO;

    // Receives the answer the next display() call will print.
    public void setResponseDTO(WordResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints what the controller set: a one-line result ("Successful", "The old meaning is
    // kept.", the "empty" line), or the translation "Vietnamese: Con Meo" (the brief's
    // screen).
    public void display() {
        // add, delete, and a translation that found nothing answer with one line
        if (responseDTO.getMessage() != null) {
            System.out.println(responseDTO.getMessage());
        }

        // a translation that found the word
        if (responseDTO.getVietnamese() != null) {
            System.out.println(String.format(Message.LABEL_VIETNAMESE,
                    responseDTO.getVietnamese()));
        }
    }
}
