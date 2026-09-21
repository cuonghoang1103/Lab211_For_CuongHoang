package service;

/**
 * SERVICE helper: the brief's "class Number" - tells whether a number is odd, even or a
 * perfect square (isOdd, isPerfectSquare, plus isEven).
 *
 * @author HE176322
 */
public class NumberChecker {

    // The brief's own rule: odd means "a % 2 != 0".
    public boolean isOdd(float number) {
        return (number % 2) != 0;
    }

    // Even: the remainder by 2 is 0 (so 0 is even).
    public boolean isEven(float number) {
        return (number % 2) == 0;
    }

    // Perfect square: a whole number that is a whole number squared (0, 1, 4, 9 ...).
    public boolean isPerfectSquare(float number) {
        long root = 0;

        // a negative number has no real square root
        if (number < 0) {
            return false;
        }

        // 0.25 is 0.5 squared, but 0.5 is not a whole number
        if (number != Math.floor(number)) {
            return false;
        }

        // the brief's Math.sqrt, rounded so that 4.9999 still counts as the root 5
        root = Math.round(Math.sqrt(number));
        return (root * root) == (long) number;
    }
}
