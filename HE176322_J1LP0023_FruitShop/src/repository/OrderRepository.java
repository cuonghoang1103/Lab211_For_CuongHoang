package repository;

import java.util.ArrayList;
import java.util.Hashtable;
import model.Item;

/**
 * REPOSITORY: the brief's two stores of the shopping side - the ArrayList of the items the
 * buyer is buying (the cart) and the Hashtable of the orders (customer name -> items
 * bought) - with simple CRUD on them. No rule, no print, no sum.
 *
 * @author HE176322
 */
public class OrderRepository {

    // The brief's hashTable.set(customer name, list of items bought).
    private Hashtable<String, ArrayList<Item>> orderMap;

    // Customer names in the order they first bought: a Hashtable has no order of its own.
    private ArrayList<String> customerNameList;

    // The brief's "ArrayList to store items that customer bought": the cart of the buyer
    // who is shopping now.
    private ArrayList<Item> cartList;

    // Creates empty stores.
    public OrderRepository() {
        orderMap = new Hashtable<>();
        customerNameList = new ArrayList<>();
        cartList = new ArrayList<>();
    }

    // Counts the customers who ordered.
    public int countOrders() {
        return customerNameList.size();
    }

    // Returns the items of one customer, or null when the name never ordered.
    public ArrayList<Item> findByCustomer(String customerName) {
        return orderMap.get(customerName);
    }

    // Stores the order of a new customer and remembers the name in order.
    public void addOrder(String customerName, ArrayList<Item> itemList) {
        orderMap.put(customerName, itemList);
        customerNameList.add(customerName);
    }

    // Replaces the items of a customer who already ordered (the name keeps its place).
    public void updateOrder(String customerName, ArrayList<Item> itemList) {
        orderMap.put(customerName, itemList);
    }

    // Returns a copy of the customer names, in the order they first bought.
    public ArrayList<String> findAllCustomers() {
        return new ArrayList<>(customerNameList);
    }

    // Returns a copy of the cart lines, in the order the fruits were chosen.
    public ArrayList<Item> findCart() {
        return new ArrayList<>(cartList);
    }

    // Finds the cart line of one fruit, ignoring the case of the id; null when the fruit
    // is not in the cart.
    public Item findCartItem(String fruitId) {
        // look at every cart line once
        for (Item item : cartList) {
            // same fruit, whatever the case of the id
            if (item.getFruitId().equalsIgnoreCase(fruitId)) {
                return item;
            }
        }

        return null;
    }

    // Appends a new line to the cart.
    public void addCartItem(Item item) {
        cartList.add(item);
    }

    // Empties the cart (after an order, or when the buyer leaves).
    public void clearCart() {
        cartList.clear();
    }
}
