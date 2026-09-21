package service;

/**
 * The three methods the brief names ("Student must implement methods additionMatrix,
 * subtractionMatrix, multiplicationMatrix"): the brief's types and order of parameters;
 * the names start with a verb (checklist 1.4), the brief's name is in the "brief:" line.
 *
 * @author HE176322
 */
public class MatrixCalculator {

    // Creates the calculator; it holds no data.
    public MatrixCalculator() {
    }

    // Function 1: adds two matrixes of the same shape, cell by cell.
    // brief: public int[][] additionMatrix(int[][] matrix1, int[][] matrix2)
    public int[][] addMatrix(int[][] matrix1Array, int[][] matrix2Array) {
        int[][] resultArray = new int[matrix1Array.length][matrix1Array[0].length];

        // every row
        for (int row = 0; row < matrix1Array.length; row++) {
            // every column of that row: add the two cells
            for (int column = 0; column < matrix1Array[0].length; column++) {
                resultArray[row][column] = matrix1Array[row][column]
                        + matrix2Array[row][column];
            }
        }

        return resultArray;
    }

    // Function 2: subtracts the second matrix from the first, cell by cell.
    // brief: public int[][] subtractionMatrix(int[][] matrix1, int[][] matrix2)
    public int[][] subtractMatrix(int[][] matrix1Array, int[][] matrix2Array) {
        int[][] resultArray = new int[matrix1Array.length][matrix1Array[0].length];

        // every row
        for (int row = 0; row < matrix1Array.length; row++) {
            // every column of that row: subtract the two cells
            for (int column = 0; column < matrix1Array[0].length; column++) {
                resultArray[row][column] = matrix1Array[row][column]
                        - matrix2Array[row][column];
            }
        }

        return resultArray;
    }

    // Function 3: multiplies (m x n) by (n x p) and gives (m x p).
    // brief: public int[][] multiplicationMatrix(int[][] matrix1, int[][] matrix2)
    public int[][] multiplyMatrix(int[][] matrix1Array, int[][] matrix2Array) {
        int shared = matrix2Array.length;
        int[][] resultArray = new int[matrix1Array.length][matrix2Array[0].length];

        // every row of matrix1 gives one row of the result
        for (int row = 0; row < matrix1Array.length; row++) {
            // every column of matrix2 gives one column of the result
            for (int column = 0; column < matrix2Array[0].length; column++) {
                int sum = 0;

                // walk along the row of matrix1 and down the column of matrix2
                for (int k = 0; k < shared; k++) {
                    sum += (matrix1Array[row][k] * matrix2Array[k][column]);
                }

                // the cell of the result
                resultArray[row][column] = sum;
            }
        }

        return resultArray;
    }
}
