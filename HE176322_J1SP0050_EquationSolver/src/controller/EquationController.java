package controller;

import dto.EquationRequestDTO;
import dto.EquationResponseDTO;
import service.EquationSolver;
import service.QuadraticEquationSolver;
import service.SuperlativeEquationSolver;
import view.EquationView;

/**
 * CONTROLLER (and FACADE): takes the coefficients from main, asks the right solver for
 * the result, and hands it to the view once. No Scanner, no print, no model.
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

    // Option 1 (the brief's calculateEquation): the solver solves ax + b = 0, the view
    // shows the result ONCE.
    public void calculateEquation(EquationRequestDTO requestDTO) {
        EquationResponseDTO responseDTO = superlativeSolver.solve(requestDTO);

        // hand the result to the view, then render it - once for the whole flow
        equationView.setResponseDTO(responseDTO);
        equationView.display();
    }

    // Option 2 (the brief's calculateQuadraticEquation): the solver solves
    // ax^2 + bx + c = 0, the view shows the result ONCE.
    public void calculateQuadraticEquation(EquationRequestDTO requestDTO) {
        EquationResponseDTO responseDTO = quadraticSolver.solve(requestDTO);

        // hand the result to the view, then render it - once for the whole flow
        equationView.setResponseDTO(responseDTO);
        equationView.display();
    }
}
