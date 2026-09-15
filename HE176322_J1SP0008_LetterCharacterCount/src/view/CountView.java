package view;

import dto.CountResponseDTO;

/**
 * VIEW: prints the result lines.
 *
 * @author HE176322
 */
public class CountView {

    // The result to display, handed over by the controller.
    private CountResponseDTO response;

    // Receives the result the next display() call will print.
    public void setResponse(CountResponseDTO response) {
        this.response = response;
    }

    // Prints one line per count, in order: words, then characters.
    public void display() {
        // one printed line per count
        for (String result : response.getResults()) {
            System.out.println(result);
        }
    }
}
