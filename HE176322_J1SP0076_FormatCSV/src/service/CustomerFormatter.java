package service;

import model.Customer;

/**
 * STRATEGY (design pattern): one way of standardising a customer row.
 *
 * @author HE176322
 */
public interface CustomerFormatter {

    // Standardises one column of the row, in place.
    void format(Customer customer);
}
