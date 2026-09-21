package repository;

import dto.MatrixRequestDTO;
import model.Matrix;

/**
 * REPOSITORY: holds the data of the program - the two matrixes the user typed, as models
 * - and only simple CRUD on them. No calculation, no print.
 *
 * @author HE176322
 */
public class MatrixRepository {

    // The matrix on the left of the operator.
    private Matrix firstMatrix;

    // The matrix on the right of the operator.
    private Matrix secondMatrix;

    // Creates the store with two empty matrixes.
    public MatrixRepository() {
        firstMatrix = new Matrix();
        secondMatrix = new Matrix();
    }

    // Create: turns the two matrixes of the request into models and keeps them in place
    // of the earlier ones.
    public void saveMatrixes(MatrixRequestDTO requestDTO) {
        firstMatrix = new Matrix(requestDTO.getFirstMatrix().getValueArray());
        secondMatrix = new Matrix(requestDTO.getSecondMatrix().getValueArray());
    }

    // Read: returns the first matrix.
    public Matrix getFirstMatrix() {
        return firstMatrix;
    }

    // Read: returns the second matrix.
    public Matrix getSecondMatrix() {
        return secondMatrix;
    }
}
