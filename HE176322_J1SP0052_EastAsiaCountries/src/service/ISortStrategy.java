package service;

import model.EastAsiaCountries;

/**
 * STRATEGY (design pattern): the common contract of every way to order the countries. The
 * name starts with "I" because it is an interface (checklist 1.3).
 *
 * @author HE176322
 */
public interface ISortStrategy {

    // Sorts the countries in place.
    void sort(EastAsiaCountries[] countryArray);
}
