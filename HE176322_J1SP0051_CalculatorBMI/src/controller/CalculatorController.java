package controller;

import dto.BMIRequestDTO;
import dto.CalculatorRequestDTO;
import dto.CalculatorResponseDTO;
import service.CalculatorService;
import view.CalculatorView;

/**
 * CONTROLLER: receives DTOs from main, asks the service to compute, and hands each result
 * to the view - one render per flow. No Scanner, no print, no model.
 *
 * @author HE176322
 */
public class CalculatorController {

    // Does the arithmetic and the BMI (Controller -> Service -> Repository -> Model).
    private CalculatorService calculatorService;

    // Prints the results.
    private CalculatorView calculatorView;

    // Creates the controller together with its service and view.
    public CalculatorController() {
        calculatorService = new CalculatorService();
        calculatorView = new CalculatorView();
    }

    // Starts a calculation: the first number goes into memory (nothing is printed - the
    // brief's screen asks for the operator right away).
    public void startCalculation(CalculatorRequestDTO requestDTO) {
        calculatorService.storeMemory(requestDTO);
    }

    // One flow "memory operator number": the view prints "Memory:" - once. Division by
    // zero throws before anything is printed.
    public void calculate(CalculatorRequestDTO requestDTO) {
        CalculatorResponseDTO responseDTO = new CalculatorResponseDTO();

        // the value in memory after this step
        responseDTO.setMemory(calculatorService.calculate(requestDTO));
        calculatorView.setResponseDTO(responseDTO);
        calculatorView.display();
    }

    // The flow of "=": the view prints "Result:" with the value in memory - once.
    public void showResult() {
        CalculatorResponseDTO responseDTO = new CalculatorResponseDTO();

        // the value the last step left in memory
        responseDTO.setResult(calculatorService.getMemory());
        calculatorView.setResponseDTO(responseDTO);
        calculatorView.display();
    }

    // Option 2: computes the BMI number and status, then the view prints them - once.
    public void calculateBMI(BMIRequestDTO requestDTO) {
        CalculatorResponseDTO responseDTO = new CalculatorResponseDTO();
        double weight = requestDTO.getWeight();
        double height = requestDTO.getHeight();

        // the number, and the brief's calculateBMI for the status band
        responseDTO.setBmiNumber(calculatorService.calculateBMIIndex(weight, height));
        responseDTO.setBmiStatus(calculatorService.calculateBMI(weight, height).getLabel());
        calculatorView.setResponseDTO(responseDTO);
        calculatorView.display();
    }
}
