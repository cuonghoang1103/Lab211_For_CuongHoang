package model;

/**
 * MODEL: a non-negative whole number of ANY length, stored as its digits.
 *
 * @author HE176322
 */
public class LargeNumber {

    // The digits 0-9, index 0 = units, index 1 = tens, and so on.
    private int[] digits;

    // JavaBean constructor: the number 0 (one digit, zero).
    public LargeNumber() {
        this.digits = new int[1];
    }

    // Wraps an existing digit array.
    public LargeNumber(int[] digits) {
        this.digits = digits;
    }

    // Returns the digits.
    public int[] getDigits() {
        return digits;
    }

    // Replaces the digits.
    public void setDigits(int[] digits) {
        this.digits = digits;
    }

    // Returns how many digit cells the number has (leading zeros included).
    public int getLength() {
        return digits.length;
    }

    // Returns one digit.
    public int getDigit(int index) {
        return digits[index];
    }

    // Polymorphism: overrides Object.toString() to write the number most significant
    // digit first, WITHOUT leading zeros.
    @Override
    public String toString() {
        int top = digits.length - 1;
        // skip leading zeros, but always keep the units digit
        while (top > 0 && digits[top] == 0) {
            top--;
        }
        StringBuilder text = new StringBuilder();
        // from the highest kept digit down to the units
        for (int i = top; i >= 0; i--) {
            text.append(digits[i]);
        }
        return text.toString();
    }
}
