package service;

import constants.Message;
import dto.ItemResponseDTO;
import dto.OrderRequestDTO;
import dto.OrderResponseDTO;
import java.util.ArrayList;
import model.Fruit;
import model.Item;
import repository.FruitRepository;
import repository.OrderRepository;

/**
 * SERVICE: the buyer's rules - the cart, the stock check and the saved orders.
 *
 * @author HE176322
 */
public class OrderService {

    // Fruits of the shop: read to sell, stock lowered after each order.
    private FruitRepository fruitRepository;
    // Saved orders (Hashtable customer name -> items).
    private OrderRepository orderRepository;
    // Cart of the buyer who is shopping now (the brief's "ArrayList to store items").
    private ArrayList<Item> cart = new ArrayList<>();

    // Creates the service on the two stores.
    public OrderService(FruitRepository fruitRepository, OrderRepository orderRepository) {
        this.fruitRepository = fruitRepository;
        this.orderRepository = orderRepository;
    }

    // Opens a shopping round with an empty cart; refuses when the shop has no fruit.
    public void startShopping() throws Exception {
        // nothing to sell yet
        if (fruitRepository.countFruits() == 0) {
            throw new Exception(Message.NO_FRUIT);
        }
        cart.clear();
    }

    // Returns the name of the fruit shown as the chosen item number.
    public String selectFruit(OrderRequestDTO requestDTO) {
        return fruitRepository.findByItemNumber(requestDTO.getItemNumber()).getFruitName();
    }

    // Puts the chosen quantity in the cart when the stock left covers it.
    public void addToCart(OrderRequestDTO requestDTO) throws Exception {
        Fruit fruit = fruitRepository.findByItemNumber(requestDTO.getItemNumber());
        int left = fruit.getQuantity() - countInCart(fruit.getFruitId());
        // nothing left: sold out, or every unit is already in this cart
        if (left <= 0) {
            throw new Exception(String.format(Message.OUT_OF_STOCK, fruit.getFruitName()));
        }
        // the buyer wants more than what is left
        if (requestDTO.getQuantity() > left) {
            throw new Exception(String.format(Message.NOT_ENOUGH, left, fruit.getFruitName()));
        }
        mergeItem(cart, new Item(fruit.getFruitId(), fruit.getFruitName(), fruit.getPrice(),
                requestDTO.getQuantity()));
    }

    // Returns the cart for the view (it has no customer name yet).
    public OrderResponseDTO getCart() {
        return toResponse(null, cart);
    }

    // Lowers the stock, saves the cart under the customer name and returns the name.
    public String placeOrder(OrderRequestDTO requestDTO) {
        // every unit bought leaves the stock
        for (Item item : cart) {
            Fruit fruit = fruitRepository.findById(item.getFruitId());
            fruit.setQuantity(fruit.getQuantity() - item.getQuantity());
        }
        String customerName = requestDTO.getCustomerName();
        ArrayList<Item> oldItems = orderRepository.findByCustomer(customerName);
        // first order of this name: a new entry in the Hashtable
        if (oldItems == null) {
            orderRepository.addOrder(customerName, new ArrayList<>(cart));
        } else {
            // the name ordered before: add the cart to the old items
            ArrayList<Item> merged = new ArrayList<>(oldItems);
            // one cart line at a time, so the same fruit stays on one line
            for (Item item : cart) {
                mergeItem(merged, item);
            }
            orderRepository.updateOrder(customerName, merged);
        }
        cart = new ArrayList<>();
        return customerName;
    }

    // Leaves the shopping screen; tells whether a non-empty cart was thrown away.
    public boolean cancelShopping() {
        boolean hadItems = !cart.isEmpty();
        cart.clear();
        return hadItems;
    }

    // Returns every saved order, customers in the order they first bought.
    public ArrayList<OrderResponseDTO> getAllOrders() throws Exception {
        // nobody has ordered yet
        if (orderRepository.countOrders() == 0) {
            throw new Exception(Message.NO_ORDER);
        }
        ArrayList<OrderResponseDTO> orders = new ArrayList<>();
        // one order per customer name
        for (String customerName : orderRepository.findAllCustomers()) {
            orders.add(toResponse(customerName, orderRepository.findByCustomer(customerName)));
        }
        return orders;
    }

    // Counts the units of one fruit already in the cart.
    private int countInCart(String fruitId) {
        int count = 0;
        // add up every cart line of this fruit
        for (Item item : cart) {
            // same fruit: its units are taken from the stock left
            if (item.getFruitId().equalsIgnoreCase(fruitId)) {
                count += item.getQuantity();
            }
        }
        return count;
    }

    // Adds a line to a list; the same fruit twice becomes one line with both quantities.
    private void mergeItem(ArrayList<Item> items, Item newItem) {
        // look for a line of the same fruit
        for (Item item : items) {
            // found: increase its quantity instead of adding a second line
            if (item.getFruitId().equalsIgnoreCase(newItem.getFruitId())) {
                item.setQuantity(item.getQuantity() + newItem.getQuantity());
                return;
            }
        }
        items.add(newItem);
    }

    // Copies the lines of a cart or order into a DTO and adds up the total.
    private OrderResponseDTO toResponse(String customerName, ArrayList<Item> items) {
        OrderResponseDTO order = new OrderResponseDTO();
        order.setCustomerName(customerName);
        double total = 0;
        // one DTO line per item, total = sum of the amounts
        for (Item item : items) {
            ItemResponseDTO line = new ItemResponseDTO();
            line.setFruitName(item.getFruitName());
            line.setQuantity(item.getQuantity());
            line.setPrice(item.getPrice());
            line.setAmount(item.getAmount());
            order.getItems().add(line);
            total += item.getAmount();
        }
        order.setTotal(total);
        return order;
    }
}
