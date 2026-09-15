package service;

import constants.Message;
import model.Matrix;

/**
 * CONCRETE STRATEGY: option 2, subtraction.
 *
 * @author HE176322
 */
public class SubtractionOperation implements MatrixOperation {

    // Holds the brief's three methods; this class uses subtractionMatrix.
    private MatrixCalculator calculator;

    // Creates the operation.
    public SubtractionOperation(MatrixCalculator calculator) {
        this.calculator = calculator;
    }

    // Subtraction needs the same number of rows AND columns.
    @Override
    public void checkSize(Matrix first, Matrix second) throws Exception {
        // different shapes cannot be subtracted cell by cell
        if (!first.hasSameSize(second)) {
            throw new Exception(Message.SIZE_NOT_SAME);
        }
    }

    // Subtracts with the brief's subtractionMatrix.
    @Override
    public int[][] calculate(Matrix first, Matrix second) {
        return calculator.subtractionMatrix(first.getValues(), second.getValues());
    }

    // Returns the subtraction symbol.
    @Override
    public String getSymbol() {
        return Message.SYMBOL_SUBTRACT;
    }
}
