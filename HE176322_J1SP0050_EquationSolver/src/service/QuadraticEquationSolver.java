package service;

import constants.Constants;
import constants.Message;
import dto.EquationRequestDTO;
import java.util.ArrayList;
import java.util.List;
import model.Equation;

/**
 * CONCRETE CLASS of the Template Method: option 2, ax^2 + bx + c = 0.
 *
 * @author HE176322
 */
public class QuadraticEquationSolver extends EquationSolver {

    // The quadratic equation has three coefficients: a, b, c.
    @Override
    protected ArrayList<Float> getCoefficientList(EquationRequestDTO requestDTO) {
        ArrayList<Float> coefficientList = new ArrayList<>();

        // in the order they were typed
        coefficientList.add(requestDTO.getCoefficientA());
        coefficientList.add(requestDTO.getCoefficientB());
        coefficientList.add(requestDTO.getCoefficientC());
        return coefficientList;
    }

    // Solves with the brief's calculateQuadraticEquation.
    @Override // brief: List<Float> is the return type of calculateQuadraticEquation
    protected List<Float> calculate(Equation equation) {
        return calculateQuadraticEquation(equation);
    }

    // The odd line of the brief's second screen: "Odd Number(s):".
    @Override
    protected String getOddLabel() {
        return Message.LABEL_ODD_QUADRATIC;
    }

    // The brief's calculateQuadraticEquation(a, b, c), reading the three coefficients from
    // the equation the repository keeps (lecturer: no method with 3 parameters).
    // brief: public List<Float> calculateQuadraticEquation(float a, float b, float c)
    private List<Float> calculateQuadraticEquation(Equation equation) {
        float coefficientA = equation.getCoefficient(Constants.INDEX_A);
        float coefficientB = equation.getCoefficient(Constants.INDEX_B);
        float coefficientC = equation.getCoefficient(Constants.INDEX_C);
        float delta = 0;
        float root = 0;
        float sqrtDelta = 0;
        ArrayList<Float> rootList = new ArrayList<>();

        // no x^2 term: this is really the equation bx + c = 0
        if (coefficientA == 0) {
            return calculateEquation(coefficientB, coefficientC);
        }

        // the discriminant: delta = b*b - 4*a*c
        delta = (coefficientB * coefficientB)
                - (Constants.DELTA_FACTOR * coefficientA * coefficientC);

        // negative delta: no real root
        if (delta < 0) {
            return null;
        }

        // delta = 0: one root of multiplicity two, listed twice
        if (delta == 0) {
            root = -coefficientB / (2 * coefficientA);
            rootList.add(root);
            rootList.add(root);
            return rootList;
        }

        // delta > 0: two different roots, (-b + sqrt(delta)) / 2a and (-b - sqrt(delta)) / 2a
        sqrtDelta = (float) Math.sqrt(delta);
        rootList.add((-coefficientB + sqrtDelta) / (2 * coefficientA));
        rootList.add((-coefficientB - sqrtDelta) / (2 * coefficientA));
        return rootList;
    }
}
