package view;

import dto.CsvResponseDTO;

/**
 * VIEW: prints the "Done" lines and the current CSV content.
 *
 * @author HE176322
 */
public class CsvView {

    // The content to display, handed over by the controller.
    private CsvResponseDTO response;

    // Receives the content the next display() call will print.
    public void setResponse(CsvResponseDTO response) {
        this.response = response;
    }

    // Prints the whole CSV content, one row per line.
    public void display() {
        System.out.println(response.getDataCSV());
    }

    // Prints a one-line result such as "Import: Done".
    public void showMessage(String message) {
        System.out.println(message);
    }
}
