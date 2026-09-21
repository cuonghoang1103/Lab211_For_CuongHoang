package model;

import constants.Constants;
import java.util.ArrayList;

/**
 * MODEL: one equation - its coefficients and its roots.
 *
 * @author HE176322
 */
public class Equation {

    // The coefficients in the order they were typed: a, b (and c).
    private ArrayList<Float> coefficientList;

    // The roots; null = no solution, empty = infinitely many solutions.
    private ArrayList<Float> rootList;

    // JavaBean constructor: an equation with no coefficients yet.
    public Equation() {
        coefficientList = new ArrayList<>();
    }

    // Creates the equation of the typed coefficients; it has no roots until it is solved.
    public Equation(ArrayList<Float> coefficientList) {
        this.coefficientList = coefficientList;
    }

    // Returns the coefficients.
    public ArrayList<Float> getCoefficientList() {
        return coefficientList;
    }

    // Replaces the coefficients.
    public void setCoefficientList(ArrayList<Float> coefficientList) {
        this.coefficientList = coefficientList;
    }

    // Returns the coefficient at one position (Constants.INDEX_A, INDEX_B or INDEX_C).
    public float getCoefficient(int index) {
        return coefficientList.get(index);
    }

    // Returns the roots.
    public ArrayList<Float> getRootList() {
        return rootList;
    }

    // Sets the roots.
    public void setRootList(ArrayList<Float> rootList) {
        this.rootList = rootList;
    }

    // Every number of the equation: the coefficients first, then the roots.
    public ArrayList<Float> getNumberList() {
        ArrayList<Float> numberList = new ArrayList<>(coefficientList);

        // no roots to add when there is no solution
        if (rootList != null) {
            numberList.addAll(rootList);
        }

        return numberList;
    }

    // Polymorphism: overrides Object.toString(); returns the text, the view prints.
    @Override
    public String toString() {
        return String.format(Constants.EQUATION_FORMAT, coefficientList, rootList);
    }
}
