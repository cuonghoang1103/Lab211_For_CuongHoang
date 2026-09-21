package service;

import constants.BMI;
import constants.Constants;
import constants.Message;
import dto.CalculatorRequestDTO;
import repository.CalculatorRepository;

/**
 * SERVICE: the arithmetic and the BMI rules - the brief's two mandated methods calculate and
 * calculateBMI live here. The temporary memory is kept by the repository. Called only by the
 * controller; no print, no keyboard.
 *
 * @author HE176322
 */
public class CalculatorService {

    // Keeps the brief's "temporary memory" (Service -> Repository -> Model).
    private CalculatorRepository calculatorRepository;

    // Creates the service with an empty memory.
    public CalculatorService() {
        calculatorRepository = new CalculatorRepository();
    }

    // Starts a new calculation: the first number goes into memory.
    public void storeMemory(CalculatorRequestDTO requestDTO) {
        calculatorRepository.saveMemory(requestDTO.getNumber());
    }

    // Returns the value in memory (for "Result:").
    public double getMemory() {
        return calculatorRepository.getMemory().getValue();
    }

    // The brief's Function 1: "memory operator number"; the result becomes the new memory
    // (the brief: "store results into the temporary memory").
    // brief: public double calculate(double a, Operator operator, double b) - a is the memory
    // (firstNumber), operator and b (secondNumber) travel in the request: the lecturer
    // allows no method with 3 parameters.
    public double calculate(CalculatorRequestDTO requestDTO) {
        double firstNumber = getMemory();
        double secondNumber = requestDTO.getNumber();
        double result = 0;

        // the brief: "Use case switch to switch (enum)"
        switch (requestDTO.getOperator()) {
            // "+"
            case ADD:
                result = firstNumber + secondNumber;
                break;

            // "-"
            case SUBTRACT:
                result = firstNumber - secondNumber;
                break;

            // "*" or "x"
            case MULTIPLY:
                result = firstNumber * secondNumber;
                break;

            // "/": doubles never throw by themselves (4.0 / 0 = Infinity)
            case DIVIDE:
                // the brief: "Use if to catch ArithmeticException divided case 0"
                if (secondNumber == 0) {
                    throw new ArithmeticException(Message.DIVIDE_BY_ZERO);
                }

                result = firstNumber / secondNumber;
                break;

            // "^": the brief says use Math.pow(a, b)
            case POWER:
                result = Math.pow(firstNumber, secondNumber);
                break;

            // "=" computes nothing: main stops before calling calculate
            default:
                result = firstNumber;
                break;
        }

        // keep the result: the next operator is applied to it
        calculatorRepository.saveMemory(result);
        return result;
    }

    // The BMI number: weight (kg) / (height (m) x height (m)).
    public double calculateBMIIndex(double weight, double height) {
        double metres = height / Constants.CM_PER_METRE;

        return weight / (metres * metres);
    }

    // The brief's calculateBMI(weight, height): the status band.
    public BMI calculateBMI(double weight, double height) {
        double bmi = calculateBMIIndex(weight, height);

        // less than 19
        if (bmi < Constants.BMI_STANDARD_MIN) {
            return BMI.UNDER_STANDARD;
        }

        // 19 up to (not including) 25
        if (bmi < Constants.BMI_OVERWEIGHT_MIN) {
            return BMI.STANDARD;
        }

        // 25 up to (not including) 30
        if (bmi < Constants.BMI_FAT_MIN) {
            return BMI.OVERWEIGHT;
        }

        // 30 up to (not including) 40
        if (bmi < Constants.BMI_VERY_FAT_MIN) {
            return BMI.FAT;
        }

        return BMI.VERY_FAT;
    }
}
