package dto;

/**
 * DTO carrying the result FROM the controller OUT TO the view: the two matrixes, the
 * operator and the result, already turned into text.
 *
 * @author HE176322
 */
public class MatrixResponseDTO {

    // The first matrix as text, one row per line.
    private String firstMatrix;
    // The operator: "+", "-" or "*".
    private String symbol;
    // The second matrix as text.
    private String secondMatrix;
    // The result matrix as text.
    private String resultMatrix;

    // JavaBean constructor: an empty response, filled through the setters.
    public MatrixResponseDTO() {
    }

    // Returns the first matrix.
    public String getFirstMatrix() {
        return firstMatrix;
    }

    // Sets the first matrix.
    public void setFirstMatrix(String firstMatrix) {
        this.firstMatrix = firstMatrix;
    }

    // Returns the operator.
    public String getSymbol() {
        return symbol;
    }

    // Sets the operator.
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    // Returns the second matrix.
    public String getSecondMatrix() {
        return secondMatrix;
    }

    // Sets the second matrix.
    public void setSecondMatrix(String secondMatrix) {
        this.secondMatrix = secondMatrix;
    }

    // Returns the result.
    public String getResultMatrix() {
        return resultMatrix;
    }

    // Sets the result.
    public void setResultMatrix(String resultMatrix) {
        this.resultMatrix = resultMatrix;
    }
}
