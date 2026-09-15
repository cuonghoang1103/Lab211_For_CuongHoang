package service;

import model.Matrix;

/**
 * STRATEGY (design pattern): the common contract of the three menu operations.
 *
 * @author HE176322
 */
public interface MatrixOperation {

    // Checks that the two shapes fit this operation.
    void checkSize(Matrix first, Matrix second) throws Exception;

    // Computes the result.
    int[][] calculate(Matrix first, Matrix second);

    // Returns the operator printed between the two matrixes.
    String getSymbol();
}
