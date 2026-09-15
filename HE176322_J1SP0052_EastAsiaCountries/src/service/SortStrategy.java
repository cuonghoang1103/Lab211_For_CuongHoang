package service;

import model.EastAsiaCountries;

/**
 * STRATEGY (design pattern): the common contract of every way to order the countries.
 *
 * @author HE176322
 */
public interface SortStrategy {

    // Sorts the countries in place.
    void sort(EastAsiaCountries[] countries);
}
