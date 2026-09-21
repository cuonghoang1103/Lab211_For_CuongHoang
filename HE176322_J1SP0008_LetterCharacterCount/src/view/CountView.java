package view;

import dto.CountResponseDTO;

/**
 * VIEW: prints the result lines. It receives the data through its attribute (the
 * ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class CountView {

    // The result to print, handed over by the controller.
    private CountResponseDTO responseDTO;

    // Receives the result the next display() will print.
    public void setResponseDTO(CountResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints one line per count, in order: words, then characters.
    public void display() {
        // one printed line per count
        for (String result : responseDTO.getResultList()) {
            System.out.println(result);
        }
    }
}
