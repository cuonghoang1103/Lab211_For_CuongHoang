package model;

import java.util.ArrayList;

/**
 * MODEL: one equation - its coefficients and its roots.
 *
 * @author HE176322
 */
public class Equation {

    // The coefficients in the order they were typed: a, b (and c).
    private ArrayList<Float> coefficients = new ArrayList<>();
    // The roots; null = no solution, empty = infinitely many solutions.
    private ArrayList<Float> roots;

    // JavaBean constructor: an equation with no coefficients yet.
    public Equation() {
    }

    // Returns the coefficients.
    public ArrayList<Float> getCoefficients() {
        return coefficients;
    }

    // Replaces the coefficients.
    public void setCoefficients(ArrayList<Float> coefficients) {
        this.coefficients = coefficients;
    }

    // Returns the roots.
    public ArrayList<Float> getRoots() {
        return roots;
    }

    // Sets the roots.
    public void setRoots(ArrayList<Float> roots) {
        this.roots = roots;
    }

    // Every number of the equation: the coefficients first, then the roots.
    public ArrayList<Float> getNumbers() {
        ArrayList<Float> numbers = new ArrayList<>(coefficients);
        // no roots to add when there is no solution
        if (roots != null) {
            numbers.addAll(roots);
        }
        return numbers;
    }

    // Polymorphism: overrides Object.toString(); returns the text, the view prints.
    @Override
    public String toString() {
        return coefficients + " -> " + roots;
    }
}
