package model;

import constants.Constants;

/**
 * MODEL: one matrix of integers - a JavaBean (private field, public no-argument
 * constructor, getter/setter), as in MVC of JSP.
 *
 * @author HE176322
 */
public class Matrix {

    // The values, values[row][column], rows and columns counted from 0.
    private int[][] values;

    // JavaBean constructor: an empty 0 x 0 matrix.
    public Matrix() {
        this.values = new int[0][0];
    }

    // Wraps an existing table of values.
    public Matrix(int[][] values) {
        this.values = values;
    }

    // Returns the values.
    public int[][] getValues() {
        return values;
    }

    // Replaces the values.
    public void setValues(int[][] values) {
        this.values = values;
    }

    // Returns the number of rows.
    public int getRows() {
        return values.length;
    }

    // Returns the number of columns (0 for a matrix without rows).
    public int getColumns() {
        // a matrix with no row has no first row to measure
        if (values.length == 0) {
            return 0;
        }
        return values[0].length;
    }

    // Tells whether the other matrix has exactly the same shape - the rule of addition
    // and subtraction.
    public boolean hasSameSize(Matrix other) {
        return getRows() == other.getRows() && getColumns() == other.getColumns();
    }

    // Tells whether this matrix can be multiplied by the other one: its number of columns
    // must equal the other's number of rows.
    public boolean canMultiplyWith(Matrix other) {
        return getColumns() == other.getRows();
    }

    // Polymorphism: overrides Object.toString() to give the brief's layout, one row per
    // line, every cell in brackets: "[2][3][2]".
    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        // one line of text per row
        for (int row = 0; row < values.length; row++) {
            // a line break goes BETWEEN rows, not after the last one
            if (row > 0) {
                text.append(System.lineSeparator());
            }
            // every cell of the row, e.g. "[3]"
            for (int value : values[row]) {
                text.append(String.format(Constants.CELL_FORMAT, value));
            }
        }
        return text.toString();
    }
}
