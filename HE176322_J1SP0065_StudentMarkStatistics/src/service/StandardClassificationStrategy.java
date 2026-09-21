package service;

import constants.Constants;

/**
 * CONCRETE STRATEGY: the brief's four bands - A: mark &gt; 7.5, B: 6 &lt;= mark &lt;=
 * 7.5, C: 4 &lt;= mark &lt; 6, D: mark &lt; 4.
 *
 * @author HE176322
 */
public class StandardClassificationStrategy implements IClassificationStrategy {

    // Classifies with a falling staircase: each test only needs its lower bound, because
    // everything above it has already returned.
    @Override
    public String classify(double average) {
        // A: strictly above 7.5 (7.5 itself is a B)
        if (average > Constants.TYPE_A_ABOVE) {
            return Constants.TYPE_A;
        }

        // B: from 6 up to 7.5
        if (average >= Constants.TYPE_B_FROM) {
            return Constants.TYPE_B;
        }

        // C: from 4 up to (not including) 6
        if (average >= Constants.TYPE_C_FROM) {
            return Constants.TYPE_C;
        }

        // D: below 4
        return Constants.TYPE_D;
    }
}
