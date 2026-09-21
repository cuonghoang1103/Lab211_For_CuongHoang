package service;

import constants.Message;
import model.Matrix;

/**
 * CONCRETE STRATEGY: option 1, addition.
 *
 * @author HE176322
 */
public class AdditionOperation implements IMatrixOperation {

    // Holds the brief's three methods; this class uses addMatrix (the brief's
    // additionMatrix).
    private MatrixCalculator calculator;

    // Creates the operation with the calculator it uses.
    public AdditionOperation(MatrixCalculator calculator) {
        this.calculator = calculator;
    }

    // Adds the two matrixes with addMatrix (the brief's additionMatrix).
    @Override
    public int[][] calculate(Matrix firstMatrix, Matrix secondMatrix) {
        return calculator.addMatrix(firstMatrix.getValueArray(),
                secondMatrix.getValueArray());
    }

    // Returns the addition symbol.
    @Override
    public String getSymbol() {
        return Message.SYMBOL_ADD;
    }
}
