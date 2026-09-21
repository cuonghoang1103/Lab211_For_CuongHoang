package model;

import java.util.Arrays;

/**
 * MODEL: the array of integers the program sorts - a JavaBean (private field, public
 * no-argument constructor, getter/setter), as in MVC of JSP. It only describes the array:
 * no print, no input.
 *
 * @author HE176322
 */
public class NumberArray {

    // The numbers, in their current order.
    private int[] valueArray;

    // JavaBean constructor: an empty array, to be filled with setValueArray.
    public NumberArray() {
        valueArray = new int[0];
    }

    // Wraps an existing array of numbers.
    public NumberArray(int[] valueArray) {
        this.valueArray = valueArray;
    }

    // Returns the numbers.
    public int[] getValueArray() {
        return valueArray;
    }

    // Replaces the numbers.
    public void setValueArray(int[] valueArray) {
        this.valueArray = valueArray;
    }

    // Returns how many numbers the array holds.
    public int getSize() {
        return valueArray.length;
    }

    // Returns the number at one position.
    public int getValue(int index) {
        return valueArray[index];
    }

    // Exchanges the numbers at two positions (the "swap" of the brief's algorithm).
    public void swap(int first, int second) {
        int temp = valueArray[first];

        // the first number waits in temp while the second one takes its place
        valueArray[first] = valueArray[second];
        valueArray[second] = temp;
    }

    // Polymorphism: overrides Object.toString() to give "[2, 6, 3]", the format of the
    // brief's screen.
    @Override
    public String toString() {
        return Arrays.toString(valueArray);
    }
}
