package dto;

/**
 * DTO carrying what the user typed FROM main INTO the controller: the chosen operation
 * and the two matrixes.
 *
 * @author HE176322
 */
public class MatrixRequestDTO {

    // The menu option: 1 addition, 2 subtraction, 3 multiplication.
    private int operation;
    // The matrix on the left of the operator.
    private MatrixDTO firstMatrix;
    // The matrix on the right of the operator.
    private MatrixDTO secondMatrix;

    // JavaBean constructor: an empty request, filled through the setters.
    public MatrixRequestDTO() {
    }

    // Returns the operation.
    public int getOperation() {
        return operation;
    }

    // Sets the operation.
    public void setOperation(int operation) {
        this.operation = operation;
    }

    // Returns the first matrix.
    public MatrixDTO getFirstMatrix() {
        return firstMatrix;
    }

    // Sets the first matrix.
    public void setFirstMatrix(MatrixDTO firstMatrix) {
        this.firstMatrix = firstMatrix;
    }

    // Returns the second matrix.
    public MatrixDTO getSecondMatrix() {
        return secondMatrix;
    }

    // Sets the second matrix.
    public void setSecondMatrix(MatrixDTO secondMatrix) {
        this.secondMatrix = secondMatrix;
    }
}
