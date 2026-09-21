package service;

import model.Customer;
import utils.StringUtils;

/**
 * CONCRETE STRATEGY (option 3): removes the redundant whitespace of the Name column and
 * makes the first character of every word upper case: "Nguyen   van a" -> "Nguyen Van A".
 *
 * @author HE176322
 */
public class NameFormatter implements ICustomerFormatter {

    // Creates the formatter; it needs no data.
    public NameFormatter() {
    }

    // Squeezes the name, then capitalises each word.
    @Override
    public void format(Customer customer) {
        String name = StringUtils.normalizeSpace(customer.getName());

        // "Nguyen van a" -> "Nguyen Van A"
        customer.setName(StringUtils.capitalizeWords(name));
    }
}
