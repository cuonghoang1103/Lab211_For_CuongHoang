package service;

import constants.Base;
import constants.Constants;
import constants.Message;
import model.BaseNumber;

/**
 * CONCRETE STRATEGY: the two hand methods of the brief's examples, for any positional
 * base (2, 10, 16). The digits were already checked by main (Validation.checkValue).
 *
 * @author HE176322
 */
public class PositionalBaseStrategy implements IBaseStrategy {

    // Text in its base -> value: digit x radix^index, summed (the brief's examples 2, 4).
    @Override
    public long convertToDecimal(BaseNumber number) throws Exception {
        String text = number.getValue().trim().toUpperCase();
        boolean negative = text.startsWith(Constants.MINUS);
        int radix = number.getBase().getRadix();
        String digits = "";
        long result = 0;
        long power = 1;
        int digit = 0;

        // a sign is not a digit: remember it and read the rest as digits
        if (negative || text.startsWith(Constants.PLUS)) {
            text = text.substring(1);
        }

        // "007" -> "7": a long run of zeros must never make the power overflow
        digits = removeLeadingZeros(text);

        // index 0 is the RIGHTMOST digit (weight radix^0 = 1)
        for (int index = 0; index < digits.length(); index++) {
            digit = Constants.DIGITS.indexOf(digits.charAt((digits.length() - 1) - index));

            // from the second digit on, the weight grows by one radix
            if (index > 0) {
                // radix^index would not fit in a long
                if (power > (Long.MAX_VALUE / radix)) {
                    throw new Exception(Message.TOO_BIG);
                }

                // the weight of this position
                power = power * radix;
            }

            // digit x power would push the sum past the largest long
            if (digit > ((Long.MAX_VALUE - result) / power)) {
                throw new Exception(Message.TOO_BIG);
            }

            // add the share of this digit to the sum
            result = result + (digit * power);
        }

        // put the sign back
        if (negative) {
            return -result;
        }

        return result;
    }

    // Value -> text in a base: repeated division, remainders read bottom-up (the brief's
    // examples 1, 3).
    @Override
    public BaseNumber convertFromDecimal(long value, Base base) {
        // safe: convertToDecimal never returns Long.MIN_VALUE (its abs is negative)
        long left = Math.abs(value);
        int radix = base.getRadix();
        StringBuilder digits = new StringBuilder();

        // zero: the division loop below would run zero times and write ""
        if (value == 0) {
            return new BaseNumber(Constants.ZERO, base);
        }

        // each division gives the NEXT digit from the right, until nothing is left
        while (left > 0) {
            digits.append(Constants.DIGITS.charAt((int) (left % radix)));
            left = left / radix;
        }

        // the sign goes in front, i.e. at the end before reversing
        if (value < 0) {
            digits.append(Constants.MINUS);
        }

        // the remainders are read bottom-up: reverse once
        return new BaseNumber(digits.reverse().toString(), base);
    }

    // Removes leading zeros but keeps at least one digit ("007" -> "7", "000" -> "0"), so
    // a long run of zeros never makes a power overflow.
    private String removeLeadingZeros(String text) {
        int start = 0;

        // skip zeros, but never the last digit
        while ((start < (text.length() - 1)) && (text.charAt(start) == '0')) {
            start++;
        }

        return text.substring(start);
    }
}
