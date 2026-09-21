package service;

import constants.Message;
import model.Matrix;

/**
 * CONCRETE STRATEGY: option 3, multiplication.
 *
 * @author HE176322
 */
public class MultiplicationOperation implements IMatrixOperation {

    // Holds the brief's three methods; this class uses multiplyMatrix (the brief's
    // multiplicationMatrix).
    private MatrixCalculator calculator;

    // Creates the operation with the calculator it uses.
    public MultiplicationOperation(MatrixCalculator calculator) {
        this.calculator = calculator;
    }

    // Multiplies with multiplyMatrix (the brief's multiplicationMatrix).
    @Override
    public int[][] calculate(Matrix firstMatrix, Matrix secondMatrix) {
        return calculator.multiplyMatrix(firstMatrix.getValueArray(),
                secondMatrix.getValueArray());
    }

    // Returns the multiplication symbol.
    @Override
    public String getSymbol() {
        return Message.SYMBOL_MULTIPLY;
    }
}
