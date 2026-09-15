package service;

import model.EastAsiaCountries;

/**
 * CONCRETE STRATEGY: the brief's order - "increasing with the country name", ignoring
 * upper/lower case - written as a bubble sort.
 *
 * @author HE176322
 */
public class NameAscendingSortStrategy implements SortStrategy {

    // Sorts the countries by name, A to Z, ignoring case.
    @Override
    public void sort(EastAsiaCountries[] countries) {
        int size = countries.length;
        // pass i moves the "biggest" remaining name to position size-1-i
        for (int i = 0; i < size - 1; i++) {
            boolean swapped = false;
            // the last i names are already in place, so stop before them
            for (int j = 0; j < size - 1 - i; j++) {
                // wrong order: swap the two neighbours
                if (countries[j].getCountryName()
                        .compareToIgnoreCase(countries[j + 1].getCountryName()) > 0) {
                    EastAsiaCountries temp = countries[j];
                    countries[j] = countries[j + 1];
                    countries[j + 1] = temp;
                    swapped = true;
                }
            }
            // a pass without any swap means the array is already sorted
            if (!swapped) {
                return;
            }
        }
    }
}
