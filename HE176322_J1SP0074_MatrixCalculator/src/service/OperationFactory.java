package service;

import constants.Constants;

/**
 * FACTORY (design pattern): the only place that knows "menu option X -> new
 * XxxOperation".
 *
 * @author HE176322
 */
public class OperationFactory {

    // Shared by every operation it creates: it owns the brief's methods.
    private MatrixCalculator calculator;

    // Creates the factory together with the calculator its operations share.
    public OperationFactory() {
        calculator = new MatrixCalculator();
    }

    // Creates the operation of a menu option.
    public IMatrixOperation createOperation(int operation) {
        // pick the concrete strategy of the menu option
        switch (operation) {
            // option 1
            case Constants.MENU_ADD:
                return new AdditionOperation(calculator);

            // option 2
            case Constants.MENU_SUBTRACT:
                return new SubtractionOperation(calculator);

            // option 3 (main only ever sends 1, 2 or 3)
            default:
                return new MultiplicationOperation(calculator);
        }
    }
}
