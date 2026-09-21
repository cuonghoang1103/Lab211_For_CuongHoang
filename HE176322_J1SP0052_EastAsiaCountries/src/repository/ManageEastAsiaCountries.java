package repository;

import constants.Constants;
import constants.Message;
import java.util.Arrays;
import model.EastAsiaCountries;

/**
 * REPOSITORY: the brief's class ManageEastAsiaCountries - it holds the countries and does
 * the simple data work on them: add, get the last one, find by name, hand out a copy.
 *
 * @author HE176322
 */
public class ManageEastAsiaCountries {

    // The stored countries; only the first count cells are used.
    private EastAsiaCountries[] countryArray;

    // How many countries are stored so far.
    private int count;

    // Creates an empty repository with room for 11 countries.
    public ManageEastAsiaCountries() {
        countryArray = new EastAsiaCountries[Constants.MAX_COUNTRIES];
        count = 0;
    }

    // Tells whether the 11 places are all taken.
    public boolean isFull() {
        return count == countryArray.length;
    }

    // The brief's addCountryInformation: stores a country after the others.
    public void addCountryInformation(EastAsiaCountries country) throws Exception {
        // no free place left in the array
        if (isFull()) {
            throw new Exception(String.format(Message.LIST_FULL, Constants.MAX_COUNTRIES));
        }

        // the next free cell, then one more country is counted
        countryArray[count] = country;
        count++;
    }

    // The brief's getRecentlyEnteredInformation: the country entered last.
    public EastAsiaCountries getRecentlyEnteredInformation() throws Exception {
        // nothing entered yet
        if (count == 0) {
            throw new Exception(Message.LIST_EMPTY);
        }

        return countryArray[count - 1];
    }

    // The brief's searchInformationByName: every country whose name contains the text,
    // ignoring case ("nam" finds "Viet Nam").
    public EastAsiaCountries[] searchInformationByName(String name) throws Exception {
        String text = name.trim().toLowerCase();
        EastAsiaCountries[] foundArray = new EastAsiaCountries[count];
        int total = 0;

        // look at every stored country once
        for (int i = 0; i < count; i++) {
            // keep it when its name contains the text
            if (countryArray[i].getCountryName().toLowerCase().contains(text)) {
                foundArray[total] = countryArray[i];
                total++;
            }
        }

        // nothing matched the text
        if (total == 0) {
            throw new Exception(String.format(Message.NOT_FOUND, name.trim()));
        }

        return Arrays.copyOf(foundArray, total);
    }

    // Hands out a COPY of the stored countries, for the service to sort.
    public EastAsiaCountries[] getAllCountries() throws Exception {
        // nothing entered yet
        if (count == 0) {
            throw new Exception(Message.LIST_EMPTY);
        }

        return Arrays.copyOf(countryArray, count);
    }
}
