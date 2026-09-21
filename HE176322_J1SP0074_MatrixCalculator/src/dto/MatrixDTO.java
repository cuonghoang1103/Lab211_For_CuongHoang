package dto;

/**
 * DTO carrying ONE matrix typed by the user: its number (1 or 2, used in the prompts
 * "Enter Row Matrix 1:") and its values.
 *
 * @author HE176322
 */
public class MatrixDTO {

    // 1 for the first matrix, 2 for the second.
    private int number;

    // The values typed by the user, valueArray[row][column].
    private int[][] valueArray;

    // JavaBean constructor: an empty matrix, filled through the setters.
    public MatrixDTO() {
    }

    // Creates the DTO of matrix number 1 or 2, still without values.
    public MatrixDTO(int number) {
        this.number = number;
    }

    // Returns the matrix number.
    public int getNumber() {
        return number;
    }

    // Sets the matrix number.
    public void setNumber(int number) {
        this.number = number;
    }

    // Returns the values.
    public int[][] getValueArray() {
        return valueArray;
    }

    // Sets the values.
    public void setValueArray(int[][] valueArray) {
        this.valueArray = valueArray;
    }
}
