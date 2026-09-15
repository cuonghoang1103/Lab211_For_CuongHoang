package service;

import constants.Base;
import constants.Constants;
import constants.Message;
import model.BaseNumber;

/**
 * CONCRETE STRATEGY: the two hand methods of the brief's examples, for any positional
 * base (2, 10, 16).
 *
 * @author HE176322
 */
public class PositionalBaseStrategy implements BaseStrategy {

    // Text in its base -> value: digit x radix^index, summed.
    @Override
    public long toDecimal(BaseNumber number) throws Exception {
        String text = number.getValue().trim().toUpperCase();
        boolean negative = text.startsWith(Constants.MINUS);
        // a sign is not a digit: remember it and read the rest as digits
        if (negative || text.startsWith(Constants.PLUS)) {
            text = text.substring(1);
        }
        // a sign alone has no digit at all
        if (text.isEmpty()) {
            throw invalid(number);
        }
        // check EVERY character first, so "1G" is "not valid", never "too big"
        for (int i = 0; i < text.length(); i++) {
            digitValue(text.charAt(i), number);
        }
        String digits = stripLeadingZeros(text);
        int radix = number.getBase().getRadix();
        long result = 0;
        long power = 1;
        // index 0 is the RIGHTMOST digit (weight radix^0 = 1)
        for (int index = 0; index < digits.length(); index++) {
            int digit = digitValue(digits.charAt(digits.length() - 1 - index), number);
            // from the second digit on, the weight grows by one radix
            if (index > 0) {
                // radix^index would not fit in a long
                if (power > Long.MAX_VALUE / radix) {
                    throw new Exception(Message.TOO_BIG);
                }
                power = power * radix;
            }
            // digit x power would push the sum past the largest long
            if (digit > (Long.MAX_VALUE - result) / power) {
                throw new Exception(Message.TOO_BIG);
            }
            result = result + digit * power;
        }
        // put the sign back
        if (negative) {
            return -result;
        }
        return result;
    }

    // Value -> text in a base: repeated division, remainders read bottom-up.
    @Override
    public BaseNumber fromDecimal(long value, Base base) {
        // zero: the division loop below would run zero times and write ""
        if (value == 0) {
            return new BaseNumber(Constants.ZERO, base);
        }
        int radix = base.getRadix();
        // safe: toDecimal never returns Long.MIN_VALUE (its abs is negative)
        long left = Math.abs(value);
        StringBuilder digits = new StringBuilder();
        // each division gives the NEXT digit from the right, until nothing is left
        while (left > 0) {
            digits.append(Constants.DIGITS.charAt((int) (left % radix)));
            left = left / radix;
        }
        // the sign goes in front, i.e. at the end before reversing
        if (value < 0) {
            digits.append(Constants.MINUS);
        }
        return new BaseNumber(digits.reverse().toString(), base);
    }

    // Value of one digit character in the number's base.
    private int digitValue(char symbol, BaseNumber number) throws Exception {
        int digit = Constants.DIGITS.indexOf(symbol);
        // not a digit, or a digit too big for this base
        if (digit < 0 || digit >= number.getBase().getRadix()) {
            throw invalid(number);
        }
        return digit;
    }

    // Removes leading zeros but keeps at least one digit ("007" -> "7", "000" -> "0"), so
    // a long run of zeros never makes a power overflow.
    private String stripLeadingZeros(String text) {
        int start = 0;
        // skip zeros, but never the last digit
        while (start < text.length() - 1 && text.charAt(start) == '0') {
            start++;
        }
        return text.substring(start);
    }

    // Builds the "not a valid number" error with the value as the user typed it.
    private Exception invalid(BaseNumber number) {
        return new Exception(String.format(Message.INVALID_VALUE, number.getValue(),
                number.getBase().getLabel()));
    }
}
