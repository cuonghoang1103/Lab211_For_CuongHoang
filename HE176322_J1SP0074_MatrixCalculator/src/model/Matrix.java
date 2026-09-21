package model;

import constants.Constants;

/**
 * MODEL: one matrix of integers - a JavaBean (private field, public no-argument
 * constructor, getter/setter), as in MVC of JSP.
 *
 * @author HE176322
 */
public class Matrix {

    // The values, valueArray[row][column], rows and columns counted from 0.
    private int[][] valueArray;

    // JavaBean constructor: an empty 0 x 0 matrix.
    public Matrix() {
        valueArray = new int[0][0];
    }

    // Wraps an existing table of values.
    public Matrix(int[][] valueArray) {
        this.valueArray = valueArray;
    }

    // Returns the values.
    public int[][] getValueArray() {
        return valueArray;
    }

    // Replaces the values.
    public void setValueArray(int[][] valueArray) {
        this.valueArray = valueArray;
    }

    // Polymorphism: overrides Object.toString() to give the brief's layout, one row per
    // line, every cell in brackets: "[2][3][2]".
    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();

        // one line of text per row
        for (int row = 0; row < valueArray.length; row++) {
            // a line break goes BETWEEN rows, not after the last one
            if (row > 0) {
                text.append(System.lineSeparator());
            }

            // every cell of the row, e.g. "[3]"
            for (int value : valueArray[row]) {
                text.append(String.format(Constants.CELL_FORMAT, value));
            }
        }

        return text.toString();
    }
}
