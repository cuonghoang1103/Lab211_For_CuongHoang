package dto;

import constants.Operator;

/**
 * DTO carrying one step of the normal calculator FROM main INTO the controller: the
 * operator and the number typed after it (the first number of a calculation has no
 * operator).
 *
 * @author HE176322
 */
public class CalculatorRequestDTO {

    // The number typed: the first number, or b of "memory operator b".
    private double number;
    // The operator typed before the number; null for the first number.
    private Operator operator;

    // JavaBean constructor: an empty request; main fills it through setters.
    public CalculatorRequestDTO() {
    }

    // Returns the number.
    public double getNumber() {
        return number;
    }

    // Sets the number.
    public void setNumber(double number) {
        this.number = number;
    }

    // Returns the operator.
    public Operator getOperator() {
        return operator;
    }

    // Sets the operator.
    public void setOperator(Operator operator) {
        this.operator = operator;
    }
}
