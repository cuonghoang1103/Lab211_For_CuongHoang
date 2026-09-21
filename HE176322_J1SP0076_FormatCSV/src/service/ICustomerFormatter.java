package service;

import model.Customer;

/**
 * STRATEGY (design pattern): one way of standardising a customer row. The name starts
 * with "I" because it is an interface (checklist 1.3).
 *
 * @author HE176322
 */
public interface ICustomerFormatter {

    // Standardises one column of the row, in place.
    void format(Customer customer);
}
