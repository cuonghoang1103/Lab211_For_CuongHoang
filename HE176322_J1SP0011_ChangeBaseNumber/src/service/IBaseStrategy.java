package service;

import constants.Base;
import model.BaseNumber;

/**
 * STRATEGY (design pattern): the contract of every way to convert between a number
 * written in some base and the number itself.
 *
 * @author HE176322
 */
public interface IBaseStrategy {

    // Reads a written number: text in its base -> the value.
    long convertToDecimal(BaseNumber number) throws Exception;

    // Writes a value in a base.
    BaseNumber convertFromDecimal(long value, Base base);
}
