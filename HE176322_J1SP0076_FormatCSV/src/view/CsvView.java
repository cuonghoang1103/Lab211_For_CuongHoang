package view;

import dto.CsvResponseDTO;

/**
 * VIEW: prints the result of every option. It receives the data through its attribute
 * (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class CsvView {

    // The result to print, handed over by the controller.
    private CsvResponseDTO responseDTO;

    // Receives the result the next display() will print.
    public void setResponseDTO(CsvResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints the result: "Import: Done", "Format: Done" or "Export: Done".
    public void display() {
        System.out.println(responseDTO.getMessage());
    }
}
