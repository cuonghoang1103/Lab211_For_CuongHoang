package service;

import model.NumberArray;

/**
 * STRATEGY (design pattern): the common contract of every sorting algorithm.
 *
 * @author HE176322
 */
public interface SortStrategy {

    // Sorts the array ascending, in place.
    void sort(NumberArray array);
}
