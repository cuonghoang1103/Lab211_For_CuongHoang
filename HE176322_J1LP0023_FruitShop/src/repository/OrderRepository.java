package repository;

import java.util.ArrayList;
import java.util.Hashtable;
import model.Item;

/**
 * REPOSITORY: the orders, kept in a Hashtable (customer name -> items bought) as the
 * brief requires.
 *
 * @author HE176322
 */
public class OrderRepository {

    // The brief's hashTable.set(customer name, list of items bought).
    private Hashtable<String, ArrayList<Item>> orders = new Hashtable<>();
    // Customer names in the order they first bought: a Hashtable has no order of its own.
    private ArrayList<String> customerNames = new ArrayList<>();

    // Creates an empty store.
    public OrderRepository() {
    }

    // Counts the customers who ordered.
    public int countOrders() {
        return customerNames.size();
    }

    // Returns the items of one customer, or null when the name never ordered.
    public ArrayList<Item> findByCustomer(String customerName) {
        return orders.get(customerName);
    }

    // Stores the order of a new customer and remembers the name in order.
    public void addOrder(String customerName, ArrayList<Item> items) {
        orders.put(customerName, items);
        customerNames.add(customerName);
    }

    // Replaces the items of a customer who already ordered (the name keeps its place).
    public void updateOrder(String customerName, ArrayList<Item> items) {
        orders.put(customerName, items);
    }

    // Returns a copy of the customer names, in the order they first bought.
    public ArrayList<String> findAllCustomers() {
        return new ArrayList<>(customerNames);
    }
}
