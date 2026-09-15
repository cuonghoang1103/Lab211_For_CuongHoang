package service;

import constants.Constants;
import dto.EquationRequestDTO;
import java.util.ArrayList;
import java.util.List;

/**
 * CONCRETE CLASS of the Template Method: option 2, ax^2 + bx + c = 0.
 *
 * @author HE176322
 */
public class QuadraticEquationSolver extends EquationSolver {

    // The quadratic equation has three coefficients: a, b, c.
    @Override
    protected ArrayList<Float> getCoefficients(EquationRequestDTO request) {
        ArrayList<Float> coefficients = new ArrayList<>();
        coefficients.add(request.getA());
        coefficients.add(request.getB());
        coefficients.add(request.getC());
        return coefficients;
    }

    // Solves with the brief's calculateQuadraticEquation.
    @Override
    // brief: List<Float> is the return type of calculateQuadraticEquation
    protected List<Float> calculate(EquationRequestDTO request) {
        return calculateQuadraticEquation(request);
    }

    // The brief's calculateQuadraticEquation(a, b, c), taking the three coefficients in
    // one DTO (lecturer: no method with 3 parameters).
    // brief: public List<Float> calculateQuadraticEquation(float a, float b, float c)
    private List<Float> calculateQuadraticEquation(EquationRequestDTO request) {
        float a = request.getA();
        float b = request.getB();
        float c = request.getC();
        // no x^2 term: this is really the equation bx + c = 0
        if (a == 0) {
            return calculateEquation(b, c);
        }
        float delta = b * b - Constants.DELTA_FACTOR * a * c;
        // negative delta: no real root
        if (delta < 0) {
            return null;
        }
        ArrayList<Float> roots = new ArrayList<>();
        // delta = 0: one root of multiplicity two, listed twice
        if (delta == 0) {
            float root = -b / (2 * a);
            roots.add(root);
            roots.add(root);
            return roots;
        }
        float sqrtDelta = (float) Math.sqrt(delta);
        roots.add((-b + sqrtDelta) / (2 * a));
        roots.add((-b - sqrtDelta) / (2 * a));
        return roots;
    }
}
