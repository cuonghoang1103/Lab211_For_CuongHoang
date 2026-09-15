package service;

import constants.Message;
import model.Matrix;

/**
 * CONCRETE STRATEGY: option 3, multiplication.
 *
 * @author HE176322
 */
public class MultiplicationOperation implements MatrixOperation {

    // Holds the brief's three methods; this class uses multiplicationMatrix.
    private MatrixCalculator calculator;

    // Creates the operation.
    public MultiplicationOperation(MatrixCalculator calculator) {
        this.calculator = calculator;
    }

    // (m x n) * (n x p): columns of the first must equal rows of the second.
    @Override
    public void checkSize(Matrix first, Matrix second) throws Exception {
        // the shared dimension n must be the same on both sides
        if (!first.canMultiplyWith(second)) {
            throw new Exception(Message.SIZE_NOT_MULTIPLY);
        }
    }

    // Multiplies with the brief's multiplicationMatrix.
    @Override
    public int[][] calculate(Matrix first, Matrix second) {
        return calculator.multiplicationMatrix(first.getValues(), second.getValues());
    }

    // Returns the multiplication symbol.
    @Override
    public String getSymbol() {
        return Message.SYMBOL_MULTIPLY;
    }
}
