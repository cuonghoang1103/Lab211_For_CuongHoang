package model;

import java.util.Arrays;

/**
 * MODEL: the array of integers the program sorts - a JavaBean (private field, public
 * no-argument constructor, getter/setter), as in MVC of JSP.
 *
 * @author HE176322
 */
public class NumberArray {

    // The numbers, in their current order.
    private int[] values;

    // JavaBean constructor: an empty array, to be filled with setValues.
    public NumberArray() {
        this.values = new int[0];
    }

    // Wraps an existing array.
    public NumberArray(int[] values) {
        this.values = values;
    }

    // Returns the numbers.
    public int[] getValues() {
        return values;
    }

    // Replaces the numbers.
    public void setValues(int[] values) {
        this.values = values;
    }

    // Returns how many numbers the array holds.
    public int getSize() {
        return values.length;
    }

    // Returns the number at one position.
    public int getValue(int index) {
        return values[index];
    }

    // Exchanges the numbers at two positions.
    public void swap(int first, int second) {
        int temp = values[first];
        values[first] = values[second];
        values[second] = temp;
    }

    // Polymorphism: overrides Object.toString() to give "[2, 6, 3]", the format of the
    // brief's screen.
    @Override
    public String toString() {
        return Arrays.toString(values);
    }
}
