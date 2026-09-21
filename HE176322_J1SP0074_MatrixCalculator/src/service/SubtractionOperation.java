package service;

import constants.Message;
import model.Matrix;

/**
 * CONCRETE STRATEGY: option 2, subtraction.
 *
 * @author HE176322
 */
public class SubtractionOperation implements IMatrixOperation {

    // Holds the brief's three methods; this class uses subtractMatrix (the brief's
    // subtractionMatrix).
    private MatrixCalculator calculator;

    // Creates the operation with the calculator it uses.
    public SubtractionOperation(MatrixCalculator calculator) {
        this.calculator = calculator;
    }

    // Subtracts with subtractMatrix (the brief's subtractionMatrix).
    @Override
    public int[][] calculate(Matrix firstMatrix, Matrix secondMatrix) {
        return calculator.subtractMatrix(firstMatrix.getValueArray(),
                secondMatrix.getValueArray());
    }

    // Returns the subtraction symbol.
    @Override
    public String getSymbol() {
        return Message.SYMBOL_SUBTRACT;
    }
}
