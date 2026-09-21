package service;

import model.Matrix;

/**
 * STRATEGY (design pattern): the common contract of the three menu operations.
 *
 * @author HE176322
 */
public interface IMatrixOperation {

    // Computes the result of this operation on the two matrixes.
    int[][] calculate(Matrix firstMatrix, Matrix secondMatrix);

    // Returns the operator printed between the two matrixes.
    String getSymbol();
}
