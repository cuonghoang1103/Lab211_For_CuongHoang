package model;

/**
 * MODEL: a non-negative whole number of ANY length, stored as its digits.
 *
 * @author HE176322
 */
public class LargeNumber {

    // The digits 0-9, index 0 = units, index 1 = tens, and so on.
    private int[] digitArray;

    // JavaBean constructor: the number 0 (one digit, zero).
    public LargeNumber() {
        this.digitArray = new int[1];
    }

    // Wraps an existing digit array.
    public LargeNumber(int[] digitArray) {
        this.digitArray = digitArray;
    }

    // Returns the digits.
    public int[] getDigitArray() {
        return digitArray;
    }

    // Replaces the digits.
    public void setDigitArray(int[] digitArray) {
        this.digitArray = digitArray;
    }

    // Returns how many digit cells the number has (leading zeros included).
    public int getLength() {
        return digitArray.length;
    }

    // Returns one digit.
    public int getDigit(int index) {
        return digitArray[index];
    }

    // Polymorphism: overrides Object.toString() to write the number most significant
    // digit first, WITHOUT leading zeros.
    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        int top = digitArray.length - 1;

        // skip leading zeros, but always keep the units digit
        while ((top > 0) && (digitArray[top] == 0)) {
            top--;
        }

        // from the highest kept digit down to the units
        for (int i = top; i >= 0; i--) {
            text.append(digitArray[i]);
        }

        return text.toString();
    }
}
