package service;

import model.NumberArray;

/**
 * STRATEGY (design pattern): the common contract of every search algorithm. The name
 * starts with "I" because it is an interface (checklist 1.3).
 *
 * @author HE176322
 */
public interface ISearchStrategy {

    // Looks for a value in the array; returns its index, or Constants.NOT_FOUND.
    int search(NumberArray numberArray, int searchValue);
}
