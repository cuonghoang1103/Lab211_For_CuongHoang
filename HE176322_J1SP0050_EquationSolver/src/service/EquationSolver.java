package service;

import dto.EquationRequestDTO;
import dto.EquationResponseDTO;
import java.util.ArrayList;
import java.util.List;
import model.Equation;

/**
 * TEMPLATE METHOD (design pattern): the fixed skeleton shared by both kinds of equation.
 *
 * @author HE176322
 */
public abstract class EquationSolver {

    // Decides odd / even / perfect square for every number.
    private NumberChecker numberChecker = new NumberChecker();

    // The template method: the whole job of options 1 and 2.
    public final EquationResponseDTO solve(EquationRequestDTO request) {
        Equation equation = new Equation();
        equation.setCoefficients(getCoefficients(request));
        // brief: the solvers return List<Float> (null / empty / roots)
        List<Float> roots = calculate(request);
        // keep "no solution" as null, copy the roots otherwise
        if (roots != null) {
            equation.setRoots(new ArrayList<>(roots));
        }
        EquationResponseDTO response = new EquationResponseDTO();
        response.setRoots(equation.getRoots());
        // put every number into the group(s) it belongs to
        for (Float number : equation.getNumbers()) {
            // the brief: odd when a % 2 != 0, otherwise even
            if (numberChecker.isOdd(number)) {
                response.getOddNumbers().add(number);
            } else {
                // remainder 0: even
                response.getEvenNumbers().add(number);
            }
            // a perfect square is listed as well, whatever its parity
            if (numberChecker.isPerfectSquare(number)) {
                response.getSquareNumbers().add(number);
            }
        }
        return response;
    }

    // Step 1 (filled by subclasses): which coefficients this equation has.
    protected abstract ArrayList<Float> getCoefficients(EquationRequestDTO request);

    // Step 2 (filled by subclasses): solves the equation.
    // brief: List<Float> is the return type of both mandated solvers
    protected abstract List<Float> calculate(EquationRequestDTO request);

    // The brief's calculateEquation: solves ax + b = 0.
    // brief: public List<Float> calculateEquation(float a, float b)
    protected List<Float> calculateEquation(float a, float b) {
        ArrayList<Float> roots = new ArrayList<>();
        // no x term: the equation is either always or never true
        if (a == 0) {
            // 0 = 0: every x is a solution -> empty list
            if (b == 0) {
                return roots;
            }
            // 0x + b = 0 with b not 0: no x works -> null
            return null;
        }
        roots.add(-b / a);
        return roots;
    }
}
