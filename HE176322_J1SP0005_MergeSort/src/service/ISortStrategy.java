package service;

import model.NumberArray;

/**
 * STRATEGY (design pattern): the common contract of every sorting algorithm. The name
 * starts with "I" because it is an interface (checklist 1.3).
 *
 * @author HE176322
 */
public interface ISortStrategy {

    // Sorts the array ascending, in place.
    void sort(NumberArray numberArray);
}
