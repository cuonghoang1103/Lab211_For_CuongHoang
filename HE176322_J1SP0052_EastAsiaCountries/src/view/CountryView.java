package view;

import constants.Constants;
import constants.Message;
import dto.CountryResponseDTO;

/**
 * VIEW: prints the answer of each menu option - the place where what the model's display()
 * returned finally reaches the screen. It receives the data through its attribute (the
 * ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class CountryView {

    // The answer to print, handed over by the controller.
    private CountryResponseDTO responseDTO;

    // Receives the answer the next display() call will print.
    public void setResponseDTO(CountryResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints what the controller set: the one-line result, or the header and one line per
    // country.
    public void display() {
        // option 1 answers with one line ("Successful")
        if (responseDTO.getMessage() != null) {
            System.out.println(responseDTO.getMessage());
        }

        // options 2, 3 and 4 answer with a table
        if (responseDTO.getRowList() != null) {
            System.out.println(String.format(Constants.HEADER_FORMAT, Message.LABEL_ID,
                    Message.LABEL_NAME, Message.LABEL_AREA, Message.LABEL_TERRAIN));

            // one line per country, text built by the model's display()
            for (String row : responseDTO.getRowList()) {
                System.out.println(row);
            }
        }
    }
}
