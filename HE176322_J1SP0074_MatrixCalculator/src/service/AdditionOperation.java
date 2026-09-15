package service;

import constants.Message;
import model.Matrix;

/**
 * CONCRETE STRATEGY: option 1, addition.
 *
 * @author HE176322
 */
public class AdditionOperation implements MatrixOperation {

    // Holds the brief's three methods; this class uses additionMatrix.
    private MatrixCalculator calculator;

    // Creates the operation.
    public AdditionOperation(MatrixCalculator calculator) {
        this.calculator = calculator;
    }

    // Addition needs the same number of rows AND columns.
    @Override
    public void checkSize(Matrix first, Matrix second) throws Exception {
        // different shapes cannot be added cell by cell
        if (!first.hasSameSize(second)) {
            throw new Exception(Message.SIZE_NOT_SAME);
        }
    }

    // Adds the two matrixes with the brief's additionMatrix.
    @Override
    public int[][] calculate(Matrix first, Matrix second) {
        return calculator.additionMatrix(first.getValues(), second.getValues());
    }

    // Returns the addition symbol.
    @Override
    public String getSymbol() {
        return Message.SYMBOL_ADD;
    }
}
