package controller;

import dto.BMIRequestDTO;
import dto.BMIResponseDTO;
import dto.CalculatorRequestDTO;
import dto.CalculatorResponseDTO;
import service.CalculatorService;
import view.CalculatorView;

/**
 * CONTROLLER: receives DTOs from main, asks the service to compute, and hands the results
 * to the view.
 *
 * @author HE176322
 */
public class CalculatorController {

    // Does the arithmetic, keeps the memory, computes BMI.
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

    // One step "memory operator number", then "Memory:" is printed.
    public void calculate(CalculatorRequestDTO requestDTO) {
        CalculatorResponseDTO response = new CalculatorResponseDTO();
        response.setMemory(calculatorService.calculate(requestDTO));
        calculatorView.setCalculatorResponse(response);
        calculatorView.displayMemory();
    }

    // "=" was typed: prints "Result:" with the value in memory.
    public void showResult() {
        CalculatorResponseDTO response = new CalculatorResponseDTO();
        response.setMemory(calculatorService.getMemory());
        calculatorView.setCalculatorResponse(response);
        calculatorView.displayResult();
    }

    // Option 2: computes the BMI number and status, then prints them.
    public void calculateBMI(BMIRequestDTO requestDTO) {
        double weight = requestDTO.getWeight();
        double height = requestDTO.getHeight();
        BMIResponseDTO response = new BMIResponseDTO();
        response.setBmiNumber(calculatorService.calculateBMIIndex(weight, height));
        response.setStatus(calculatorService.calculateBMI(weight, height).getLabel());
        calculatorView.setBmiResponse(response);
        calculatorView.displayBMI();
    }
}
