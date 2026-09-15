package service;

import constants.BMI;
import constants.Constants;
import constants.Message;
import dto.CalculatorRequestDTO;
import model.CalculatorMemory;

/**
 * SERVICE: the arithmetic, the temporary memory and the BMI rules - the brief's two
 * mandated methods calculate and calculateBMI live here.
 *
 * @author HE176322
 */
public class CalculatorService {

    // The brief's "temporary memory"; each calculate stores its result here.
    private CalculatorMemory memory = new CalculatorMemory();

    // Starts a new calculation: the first number goes into memory.
    public void storeMemory(CalculatorRequestDTO requestDTO) {
        memory.setValue(requestDTO.getNumber());
    }

    // Returns the value in memory (for "Result:").
    public double getMemory() {
        return memory.getValue();
    }

    // The brief's calculate(a, operator, b).
    public double calculate(CalculatorRequestDTO requestDTO) {
        double a = memory.getValue();
        double b = requestDTO.getNumber();
        double result;
        // the brief: "Use case switch to switch (enum)"
        switch (requestDTO.getOperator()) {
            // "+"
            case ADD:
                result = a + b;
                break;
            // "-"
            case SUBTRACT:
                result = a - b;
                break;
            // "*" or "x"
            case MULTIPLY:
                result = a * b;
                break;
            // "/": doubles never throw by themselves (4.0 / 0 = Infinity)
            case DIVIDE:
                // the brief: "Use if to catch ArithmeticException divided case 0"
                if (b == 0) {
                    throw new ArithmeticException(Message.DIVIDE_BY_ZERO);
                }
                result = a / b;
                break;
            // "^": the brief says use Math.pow(a, b)
            case POWER:
                result = Math.pow(a, b);
                break;
            // "=" computes nothing: main stops before calling calculate
            default:
                result = a;
                break;
        }
        memory.setValue(result);
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
