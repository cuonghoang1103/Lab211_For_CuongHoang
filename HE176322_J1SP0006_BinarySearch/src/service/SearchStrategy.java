package service;

import model.NumberArray;

/**
 * STRATEGY (design pattern): the common contract of every search algorithm.
 *
 * @author HE176322
 */
public interface SearchStrategy {

    // Looks for a value in the array.
    int search(NumberArray array, int value);
}
