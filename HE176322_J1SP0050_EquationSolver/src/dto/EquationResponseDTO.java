package dto;

import java.util.ArrayList;

/**
 * DTO carrying the result FROM the controller OUT TO the view: the roots and the three
 * groups of numbers.
 *
 * @author HE176322
 */
public class EquationResponseDTO {

    // The roots; null = no solution, empty = infinitely many solutions.
    private ArrayList<Float> roots;
    // Numbers of the equation that are odd (the brief: a % 2 != 0).
    private ArrayList<Float> oddNumbers = new ArrayList<>();
    // Numbers of the equation that are even.
    private ArrayList<Float> evenNumbers = new ArrayList<>();
    // Numbers of the equation that are perfect squares.
    private ArrayList<Float> squareNumbers = new ArrayList<>();

    // JavaBean constructor: an empty result, filled through the setters.
    public EquationResponseDTO() {
    }

    // Returns the roots.
    public ArrayList<Float> getRoots() {
        return roots;
    }

    // Sets the roots.
    public void setRoots(ArrayList<Float> roots) {
        this.roots = roots;
    }

    // Returns the odd numbers.
    public ArrayList<Float> getOddNumbers() {
        return oddNumbers;
    }

    // Sets the odd numbers.
    public void setOddNumbers(ArrayList<Float> oddNumbers) {
        this.oddNumbers = oddNumbers;
    }

    // Returns the even numbers.
    public ArrayList<Float> getEvenNumbers() {
        return evenNumbers;
    }

    // Sets the even numbers.
    public void setEvenNumbers(ArrayList<Float> evenNumbers) {
        this.evenNumbers = evenNumbers;
    }

    // Returns the perfect squares.
    public ArrayList<Float> getSquareNumbers() {
        return squareNumbers;
    }

    // Sets the perfect squares.
    public void setSquareNumbers(ArrayList<Float> squareNumbers) {
        this.squareNumbers = squareNumbers;
    }
}
