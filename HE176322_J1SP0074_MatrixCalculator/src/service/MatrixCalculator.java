package service;

/**
 * The three methods the brief names ("Student must implement methods additionMatrix,
 * subtractionMatrix, multiplicationMatrix"), with exactly the brief's signatures.
 *
 * @author HE176322
 */
public class MatrixCalculator {

    // Creates the calculator; it holds no data.
    public MatrixCalculator() {
    }

    // Function 1: adds two matrixes of the same shape, cell by cell.
    public int[][] additionMatrix(int[][] matrix1, int[][] matrix2) {
        int[][] result = new int[matrix1.length][matrix1[0].length];
        // every row
        for (int row = 0; row < matrix1.length; row++) {
            // every column of that row: add the two cells
            for (int column = 0; column < matrix1[0].length; column++) {
                result[row][column] = matrix1[row][column] + matrix2[row][column];
            }
        }
        return result;
    }

    // Function 2: subtracts the second matrix from the first, cell by cell.
    public int[][] subtractionMatrix(int[][] matrix1, int[][] matrix2) {
        int[][] result = new int[matrix1.length][matrix1[0].length];
        // every row
        for (int row = 0; row < matrix1.length; row++) {
            // every column of that row: subtract the two cells
            for (int column = 0; column < matrix1[0].length; column++) {
                result[row][column] = matrix1[row][column] - matrix2[row][column];
            }
        }
        return result;
    }

    // Function 3: multiplies (m x n) by (n x p) and gives (m x p).
    public int[][] multiplicationMatrix(int[][] matrix1, int[][] matrix2) {
        int shared = matrix2.length;
        int[][] result = new int[matrix1.length][matrix2[0].length];
        // every row of matrix1 gives one row of the result
        for (int row = 0; row < matrix1.length; row++) {
            // every column of matrix2 gives one column of the result
            for (int column = 0; column < matrix2[0].length; column++) {
                int sum = 0;
                // walk along the row of matrix1 and down the column of matrix2
                for (int k = 0; k < shared; k++) {
                    sum += matrix1[row][k] * matrix2[k][column];
                }
                result[row][column] = sum;
            }
        }
        return result;
    }
}
