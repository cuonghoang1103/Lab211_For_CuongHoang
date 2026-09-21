package dto;

import java.util.ArrayList;

/**
 * DTO carrying the result FROM the controller OUT TO the view: the roots, the label of
 * the odd line and the three groups of numbers.
 *
 * @author HE176322
 */
public class EquationResponseDTO {

    // The roots; null = no solution, empty = infinitely many solutions.
    private ArrayList<Float> rootList;

    // Format of the odd line: the brief's two screens spell its label differently.
    private String oddLabel;

    // Numbers of the equation that are odd (the brief: a % 2 != 0).
    private ArrayList<Float> oddNumberList = new ArrayList<>();

    // Numbers of the equation that are even.
    private ArrayList<Float> evenNumberList = new ArrayList<>();

    // Numbers of the equation that are perfect squares.
    private ArrayList<Float> squareNumberList = new ArrayList<>();

    // JavaBean constructor: an empty result, filled through the setters.
    public EquationResponseDTO() {
    }

    // Returns the roots.
    public ArrayList<Float> getRootList() {
        return rootList;
    }

    // Sets the roots.
    public void setRootList(ArrayList<Float> rootList) {
        this.rootList = rootList;
    }

    // Returns the format of the odd line.
    public String getOddLabel() {
        return oddLabel;
    }

    // Sets the format of the odd line.
    public void setOddLabel(String oddLabel) {
        this.oddLabel = oddLabel;
    }

    // Returns the odd numbers.
    public ArrayList<Float> getOddNumberList() {
        return oddNumberList;
    }

    // Sets the odd numbers.
    public void setOddNumberList(ArrayList<Float> oddNumberList) {
        this.oddNumberList = oddNumberList;
    }

    // Returns the even numbers.
    public ArrayList<Float> getEvenNumberList() {
        return evenNumberList;
    }

    // Sets the even numbers.
    public void setEvenNumberList(ArrayList<Float> evenNumberList) {
        this.evenNumberList = evenNumberList;
    }

    // Returns the perfect squares.
    public ArrayList<Float> getSquareNumberList() {
        return squareNumberList;
    }

    // Sets the perfect squares.
    public void setSquareNumberList(ArrayList<Float> squareNumberList) {
        this.squareNumberList = squareNumberList;
    }
}
