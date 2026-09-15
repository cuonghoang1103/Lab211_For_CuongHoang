package model;

import constants.Base;
import constants.Constants;
import constants.Message;

/**
 * MODEL: a number WRITTEN in one base - its digits as text and the base they belong to.
 *
 * @author HE176322
 */
public class BaseNumber {

    // The digits as written.
    private String value;
    // The base the digits are written in.
    private Base base;

    // JavaBean constructor: the number 0 in decimal.
    public BaseNumber() {
        this.value = Constants.ZERO;
        this.base = Base.DECIMAL;
    }

    // Creates a number from its text and its base.
    public BaseNumber(String value, Base base) {
        this.value = value;
        this.base = base;
    }

    // Returns the digits as written.
    public String getValue() {
        return value;
    }

    // Changes the digits.
    public void setValue(String value) {
        this.value = value;
    }

    // Returns the base.
    public Base getBase() {
        return base;
    }

    // Changes the base.
    public void setBase(Base base) {
        this.base = base;
    }

    // Polymorphism: overrides Object.toString() to give "535 (DEC)", the notation of the
    // brief's examples.
    @Override
    public String toString() {
        return String.format(Message.VALUE_FORMAT, value, base.getLabel());
    }
}
