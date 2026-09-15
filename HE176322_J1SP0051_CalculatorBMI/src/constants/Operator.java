package constants;

/**
 * The six operators the user may type in the normal calculator.
 *
 * @author HE176322
 */
public enum Operator {

    // Addition, typed "+".
    ADD("+"),
    // Subtraction, typed "-".
    SUBTRACT("-"),
    // Multiplication, typed "*" (or "x", see Validation.checkOperator).
    MULTIPLY("*"),
    // Division, typed "/".
    DIVIDE("/"),
    // Exponent, typed "^" (Math.pow).
    POWER("^"),
    // "=": stop and show the result.
    EQUAL("=");

    // The symbol the user types.
    private final String symbol;

    // Creates one operator constant; private because only the six constants above may
    // exist.
    private Operator(String symbol) {
        this.symbol = symbol;
    }

    // Returns the symbol the user types.
    public String getSymbol() {
        return symbol;
    }
}
