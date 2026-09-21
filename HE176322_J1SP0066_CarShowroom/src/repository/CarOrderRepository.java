package repository;

import java.util.ArrayList;
import model.CarOrder;

/**
 * REPOSITORY: holds the data of the program - the orders the showroom has sold - and only
 * simple CRUD on it. No rule, no print.
 *
 * @author HE176322
 */
public class CarOrderRepository {

    // Every order sold during this run, in order.
    private ArrayList<CarOrder> orderList;

    // Creates an empty record of sales.
    public CarOrderRepository() {
        orderList = new ArrayList<>();
    }

    // Create: stores an order the showroom has just sold.
    public void addOrder(CarOrder order) {
        orderList.add(order);
    }
}
