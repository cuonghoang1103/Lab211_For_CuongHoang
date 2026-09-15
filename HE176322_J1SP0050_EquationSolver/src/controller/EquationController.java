package controller;

import constants.Message;
import dto.EquationRequestDTO;
import service.EquationSolver;
import service.QuadraticEquationSolver;
import service.SuperlativeEquationSolver;
import view.EquationView;

/**
 * CONTROLLER (and FACADE): takes the coefficients from main, asks the right solver for
 * the result, and hands it to the view.
 *
 * @author HE176322
 */
public class EquationController {

    // Solves ax + b = 0 (option 1).
    private EquationSolver superlativeSolver;
    // Solves ax^2 + bx + c = 0 (option 2).
    private EquationSolver quadraticSolver;
    // Prints the result.
    private EquationView equationView;

    // Creates the controller with one solver per menu option and the view.
    public EquationController() {
        superlativeSolver = new SuperlativeEquationSolver();
        quadraticSolver = new QuadraticEquationSolver();
        equationView = new EquationView();
    }

    // Option 1 (the brief's calculateEquation): solves ax + b = 0 and shows the result.
    public void calculateEquation(EquationRequestDTO requestDTO) {
        equationView.setResponse(superlativeSolver.solve(requestDTO));
        equationView.setOddLabel(Message.LABEL_ODD);
        equationView.display();
    }

    // Option 2 (the brief's calculateQuadraticEquation): solves ax^2 + bx + c = 0 and
    // shows the result.
    public void calculateQuadraticEquation(EquationRequestDTO requestDTO) {
        equationView.setResponse(quadraticSolver.solve(requestDTO));
        equationView.setOddLabel(Message.LABEL_ODD_QUADRATIC);
        equationView.display();
    }
}
