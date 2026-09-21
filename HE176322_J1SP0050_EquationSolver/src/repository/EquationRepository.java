package repository;

import java.util.ArrayList;
import model.Equation;

/**
 * REPOSITORY: holds the data of the program - the equation being solved (the coefficients
 * typed by the user, then its roots) - and only simple CRUD on it. No formula, no print.
 *
 * @author HE176322
 */
public class EquationRepository {

    // The equation the solver works on (the model).
    private Equation equation;

    // Creates the store with an empty equation.
    public EquationRepository() {
        equation = new Equation();
    }

    // Create: wraps the typed coefficients in the model and keeps it in place of the
    // equation of an earlier option.
    public void saveEquation(ArrayList<Float> coefficientList) {
        equation = new Equation(coefficientList);
    }

    // Read: returns the equation kept by the last save.
    public Equation getEquation() {
        return equation;
    }
}
