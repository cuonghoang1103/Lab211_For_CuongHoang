package service;

import dto.EquationRequestDTO;
import dto.EquationResponseDTO;
import java.util.ArrayList;
import java.util.List;
import model.Equation;
import repository.EquationRepository;

/**
 * TEMPLATE METHOD (design pattern): the fixed skeleton shared by both kinds of equation.
 * Called only by the controller; no print, no keyboard.
 *
 * @author HE176322
 */
public abstract class EquationSolver {

    // Decides odd / even / perfect square for every number.
    private NumberChecker numberChecker;

    // Keeps the equation being solved (Service -> Repository -> Model).
    private EquationRepository equationRepository;

    // Creates the solver with its number checker and an empty repository; only the
    // subclasses call it.
    protected EquationSolver() {
        numberChecker = new NumberChecker();
        equationRepository = new EquationRepository();
    }

    // The template method: the whole job of options 1 and 2.
    public final EquationResponseDTO solve(EquationRequestDTO requestDTO) {
        // brief: the solvers return List<Float> (null / empty / roots)
        List<Float> rootList = null;
        EquationResponseDTO responseDTO = new EquationResponseDTO();
        Equation equation = null;

        // step 1: keep the typed coefficients in the repository, then work on the equation
        // it holds
        equationRepository.saveEquation(getCoefficientList(requestDTO));
        equation = equationRepository.getEquation();

        // step 2: solve it with the brief's method of the subclass
        rootList = calculate(equation);

        // null means "no solution" and stays null; the roots are copied otherwise
        if (rootList != null) {
            equation.setRootList(new ArrayList<>(rootList));
        }

        // step 3: the roots and the label of the odd line go to the view
        responseDTO.setRootList(equation.getRootList());
        responseDTO.setOddLabel(getOddLabel());

        // put every number (coefficients first, then roots) into the group(s) it belongs to
        for (Float number : equation.getNumberList()) {
            // the brief: odd when a % 2 != 0, otherwise even
            if (numberChecker.isOdd(number)) {
                responseDTO.getOddNumberList().add(number);
            } else {
                // remainder 0: even
                responseDTO.getEvenNumberList().add(number);
            }

            // a perfect square is listed as well, whatever its parity
            if (numberChecker.isPerfectSquare(number)) {
                responseDTO.getSquareNumberList().add(number);
            }
        }

        return responseDTO;
    }

    // Step 1 (filled by subclasses): which coefficients this equation has.
    protected abstract ArrayList<Float> getCoefficientList(EquationRequestDTO requestDTO);

    // Step 2 (filled by subclasses): solves the equation the repository keeps.
    // brief: List<Float> is the return type of both mandated solvers
    protected abstract List<Float> calculate(Equation equation);

    // Step 3 (filled by subclasses): the label of the odd line - the brief's two screens
    // spell it differently.
    protected abstract String getOddLabel();

    // The brief's calculateEquation: solves ax + b = 0.
    // brief: public List<Float> calculateEquation(float a, float b)
    protected List<Float> calculateEquation(float coefficientA, float coefficientB) {
        ArrayList<Float> rootList = new ArrayList<>();

        // no x term: the equation is either always or never true
        if (coefficientA == 0) {
            // 0 = 0: every x is a solution -> empty list
            if (coefficientB == 0) {
                return rootList;
            }

            // 0x + b = 0 with b not 0: no x works -> null
            return null;
        }

        // one root: x = -b / a
        rootList.add(-coefficientB / coefficientA);
        return rootList;
    }
}
