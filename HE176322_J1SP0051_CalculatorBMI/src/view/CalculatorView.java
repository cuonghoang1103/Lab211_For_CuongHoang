package view;

import constants.Constants;
import constants.Message;
import dto.BMIResponseDTO;
import dto.CalculatorResponseDTO;
import java.util.Locale;

/**
 * VIEW: prints the "Memory:", "Result:" and BMI lines.
 *
 * @author HE176322
 */
public class CalculatorView {

    // The value in memory to display, handed over by the controller.
    private CalculatorResponseDTO calculatorResponse;
    // The BMI result to display, handed over by the controller.
    private BMIResponseDTO bmiResponse;

    // Receives the memory value the next displayMemory/displayResult prints.
    public void setCalculatorResponse(CalculatorResponseDTO calculatorResponse) {
        this.calculatorResponse = calculatorResponse;
    }

    // Receives the BMI result the next displayBMI prints.
    public void setBmiResponse(BMIResponseDTO bmiResponse) {
        this.bmiResponse = bmiResponse;
    }

    // Prints "Memory:8.0" after one calculation step.
    public void displayMemory() {
        System.out.println(Message.LABEL_MEMORY + calculatorResponse.getMemory());
    }

    // Prints "Result:24.0" when "=" is typed.
    public void displayResult() {
        System.out.println(Message.LABEL_RESULT + calculatorResponse.getMemory());
    }

    // Prints the BMI number (2 decimals, always with a dot) and the status.
    public void displayBMI() {
        System.out.println(Message.LABEL_BMI_NUMBER + String.format(Locale.US,
                Constants.BMI_FORMAT, bmiResponse.getBmiNumber()));
        System.out.println(Message.LABEL_BMI_STATUS + bmiResponse.getStatus());
    }
}
