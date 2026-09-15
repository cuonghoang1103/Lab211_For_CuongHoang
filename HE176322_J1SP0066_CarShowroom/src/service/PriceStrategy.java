package service;

import model.CarOrder;

/**
 * STRATEGY (design pattern): the contract of any rule that decides the lowest price the
 * showroom accepts for an order.
 *
 * @author HE176322
 */
public interface PriceStrategy {

    // Returns the lowest price accepted for the order's car and colour.
    double getAskingPrice(CarOrder order);
}
