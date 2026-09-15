package service;

import constants.Base;
import model.BaseNumber;

/**
 * STRATEGY (design pattern): the contract of every way to convert between a number
 * written in some base and the number itself.
 *
 * @author HE176322
 */
public interface BaseStrategy {

    // Reads a written number: text in its base -> the value.
    long toDecimal(BaseNumber number) throws Exception;

    // Writes a value in a base.
    BaseNumber fromDecimal(long value, Base base);
}
