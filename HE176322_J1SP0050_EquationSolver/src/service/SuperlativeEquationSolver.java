package service;

import dto.EquationRequestDTO;
import java.util.ArrayList;
import java.util.List;

/**
 * CONCRETE CLASS of the Template Method: option 1, ax + b = 0.
 *
 * @author HE176322
 */
public class SuperlativeEquationSolver extends EquationSolver {

    // The superlative equation has two coefficients: a, b.
    @Override
    protected ArrayList<Float> getCoefficients(EquationRequestDTO request) {
        ArrayList<Float> coefficients = new ArrayList<>();
        coefficients.add(request.getA());
        coefficients.add(request.getB());
        return coefficients;
    }

    // Solves with the brief's calculateEquation(a, b).
    @Override
    // brief: List<Float> is the return type of calculateEquation
    protected List<Float> calculate(EquationRequestDTO request) {
        return calculateEquation(request.getA(), request.getB());
    }
}
