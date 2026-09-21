package service;

import constants.Constants;
import constants.Message;
import dto.EquationRequestDTO;
import java.util.ArrayList;
import java.util.List;
import model.Equation;

/**
 * CONCRETE CLASS of the Template Method: option 1, ax + b = 0.
 *
 * @author HE176322
 */
public class SuperlativeEquationSolver extends EquationSolver {

    // The superlative equation has two coefficients: a, b.
    @Override
    protected ArrayList<Float> getCoefficientList(EquationRequestDTO requestDTO) {
        ArrayList<Float> coefficientList = new ArrayList<>();

        // in the order they were typed
        coefficientList.add(requestDTO.getCoefficientA());
        coefficientList.add(requestDTO.getCoefficientB());
        return coefficientList;
    }

    // Solves with the brief's calculateEquation(a, b), reading a and b from the equation the
    // repository keeps.
    @Override // brief: List<Float> is the return type of calculateEquation
    protected List<Float> calculate(Equation equation) {
        return calculateEquation(equation.getCoefficient(Constants.INDEX_A),
                equation.getCoefficient(Constants.INDEX_B));
    }

    // The odd line of the brief's first screen: "Number is Odd:".
    @Override
    protected String getOddLabel() {
        return Message.LABEL_ODD;
    }
}
