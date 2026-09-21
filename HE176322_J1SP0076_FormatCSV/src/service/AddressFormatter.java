package service;

import model.Customer;
import utils.StringUtils;

/**
 * CONCRETE STRATEGY (option 2): removes the redundant whitespace of the Address column:
 * "Cau Giay     - Ha    Noi" -> "Cau Giay - Ha Noi".
 *
 * @author HE176322
 */
public class AddressFormatter implements ICustomerFormatter {

    // Creates the formatter; it needs no data.
    public AddressFormatter() {
    }

    // Squeezes the address to single spaces.
    @Override
    public void format(Customer customer) {
        customer.setAddress(StringUtils.normalizeSpace(customer.getAddress()));
    }
}
