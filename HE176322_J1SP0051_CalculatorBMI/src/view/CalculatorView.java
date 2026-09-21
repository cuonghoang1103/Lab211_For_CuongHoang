package view;

import constants.Message;
import dto.CalculatorResponseDTO;
import java.util.Locale;

/**
 * VIEW: prints the "Memory:", "Result:" and BMI lines. It receives the data through its
 * attribute (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class CalculatorView {

    // The answer to print, handed over by the controller.
    private CalculatorResponseDTO responseDTO;

    // Receives the answer the next display() call will print.
    public void setResponseDTO(CalculatorResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints what the controller set: "Memory:8.0" after a step, "Result:24.0" after "=",
    // or the BMI number and status.
    public void display() {
        // one step of the normal calculator
        if (responseDTO.getMemory() != null) {
            System.out.println(String.format(Message.LABEL_MEMORY, responseDTO.getMemory()));
        }

        // "=" was typed
        if (responseDTO.getResult() != null) {
            System.out.println(String.format(Message.LABEL_RESULT, responseDTO.getResult()));
        }

        // the BMI number (2 decimals, always with a dot: Locale.US) and the status
        if (responseDTO.getBmiStatus() != null) {
            System.out.println(String.format(Locale.US, Message.LABEL_BMI_NUMBER,
                    responseDTO.getBmiNumber()));
            System.out.println(String.format(Message.LABEL_BMI_STATUS,
                    responseDTO.getBmiStatus()));
        }
    }
}
