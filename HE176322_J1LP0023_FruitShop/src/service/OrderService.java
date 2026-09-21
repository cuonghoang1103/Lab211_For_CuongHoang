package service;

import constants.Message;
import dto.ItemResponseDTO;
import dto.OrderRequestDTO;
import dto.OrderResponseDTO;
import dto.ShopResponseDTO;
import java.util.ArrayList;
import model.Fruit;
import model.Item;
import repository.FruitRepository;
import repository.OrderRepository;

/**
 * SERVICE: the buyer's rules - the stock check, the cart, the money (amounts and totals)
 * and the saved orders. The data itself stays in the two repositories. No print, no
 * keyboard.
 *
 * @author HE176322
 */
public class OrderService {

    // Fruits of the shop: read to sell, stock lowered after each order.
    private FruitRepository fruitRepository;

    // The cart of the buyer shopping now and the saved orders.
    private OrderRepository orderRepository;

    // Creates the service on the two stores.
    public OrderService(FruitRepository fruitRepository, OrderRepository orderRepository) {
        this.fruitRepository = fruitRepository;
        this.orderRepository = orderRepository;
    }

    // Option 3, an item chosen: the brief's "You selected: Coconut".
    public ShopResponseDTO selectFruit(OrderRequestDTO requestDTO) {
        ShopResponseDTO responseDTO = new ShopResponseDTO();
        Fruit fruit = fruitRepository.findByItemNumber(requestDTO.getItemNumber());

        responseDTO.setMessage(String.format(Message.SELECTED, fruit.getFruitName()));
        return responseDTO;
    }

    // Option 3, a quantity typed: what is left (stock - what this cart already holds) must
    // cover it; then it goes in the cart, on the line of the same fruit if there is one.
    public void addToCart(OrderRequestDTO requestDTO) throws Exception {
        Fruit fruit = fruitRepository.findByItemNumber(requestDTO.getItemNumber());
        Item cartItem = orderRepository.findCartItem(fruit.getFruitId());
        int inCart = (cartItem == null) ? 0 : cartItem.getQuantity();
        int left = fruit.getQuantity() - inCart;

        // nothing left: sold out, or every unit is already in this cart
        if (left <= 0) {
            throw new Exception(String.format(Message.OUT_OF_STOCK, fruit.getFruitName()));
        }

        // the buyer wants more than what is left
        if (requestDTO.getQuantity() > left) {
            throw new Exception(String.format(Message.NOT_ENOUGH, left, fruit.getFruitName()));
        }

        // the same fruit twice stays one line: its quantity grows
        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + requestDTO.getQuantity());
        } else {
            // a new fruit: a new line, with the price of today
            orderRepository.addCartItem(new Item(fruit.getFruitId(), fruit.getFruitName(),
                    fruit.getPrice(), requestDTO.getQuantity()));
        }
    }

    // Option 3, the answer Y: the cart with its total, before the name is asked.
    public ShopResponseDTO getCart() {
        ShopResponseDTO responseDTO = new ShopResponseDTO();

        // the cart has no customer name yet
        responseDTO.setCart(convertToOrder(null, orderRepository.findCart()));
        return responseDTO;
    }

    // Option 3, the name typed: every unit bought leaves the stock, the cart is saved
    // under the name (a name that ordered before gets the cart added to its order), the
    // cart is emptied, and the answer thanks the customer.
    public ShopResponseDTO saveOrder(OrderRequestDTO requestDTO) {
        ShopResponseDTO responseDTO = new ShopResponseDTO();
        String customerName = requestDTO.getCustomerName();
        ArrayList<Item> cartList = orderRepository.findCart();
        ArrayList<Item> oldItemList = orderRepository.findByCustomer(customerName);
        Fruit fruit = null;

        // every unit bought leaves the stock
        for (Item item : cartList) {
            fruit = fruitRepository.findById(item.getFruitId());
            fruit.setQuantity(fruit.getQuantity() - item.getQuantity());
        }

        // first order of this name: a new entry in the Hashtable
        if (oldItemList == null) {
            orderRepository.addOrder(customerName, cartList);
        } else {
            // the name ordered before: the cart is added to its old order
            orderRepository.updateOrder(customerName, mergeItems(oldItemList, cartList));
        }

        // the cart is an order now: the next buyer starts with an empty one
        orderRepository.clearCart();
        responseDTO.setMessage(String.format(Message.ORDER_SUCCESS, customerName));
        return responseDTO;
    }

    // Option 3, item 0: the buyer leaves; a cart with fruits is thrown away and the answer
    // says so, an empty cart leaves without a word.
    public ShopResponseDTO cancelShopping() {
        ShopResponseDTO responseDTO = new ShopResponseDTO();

        // only a cart that had fruits is worth a message
        if (!orderRepository.findCart().isEmpty()) {
            responseDTO.setMessage(Message.ORDER_CANCELLED);
        }

        orderRepository.clearCart();
        return responseDTO;
    }

    // Option 2: every saved order, customers in the order they first bought; nobody has
    // ordered yet is thrown ("There is no order yet.").
    public ShopResponseDTO getAllOrders() throws Exception {
        ShopResponseDTO responseDTO = new ShopResponseDTO();
        ArrayList<OrderResponseDTO> orderList = new ArrayList<>();

        // nobody has ordered yet
        if (orderRepository.countOrders() == 0) {
            throw new Exception(Message.NO_ORDER);
        }

        // one order per customer name
        for (String customerName : orderRepository.findAllCustomers()) {
            orderList.add(convertToOrder(customerName,
                    orderRepository.findByCustomer(customerName)));
        }

        responseDTO.setOrderList(orderList);
        return responseDTO;
    }

    // Adds the cart lines to an old order: the same fruit stays one line (its quantity
    // grows), a fruit this customer never bought becomes a new line.
    private ArrayList<Item> mergeItems(ArrayList<Item> oldItemList, ArrayList<Item> cartList) {
        ArrayList<Item> mergedList = new ArrayList<>(oldItemList);
        Item oldItem = null;

        // one cart line at a time
        for (Item cartItem : cartList) {
            oldItem = findItem(mergedList, cartItem.getFruitId());

            // the fruit is already in the order: add the units to its line
            if (oldItem != null) {
                oldItem.setQuantity(oldItem.getQuantity() + cartItem.getQuantity());
            } else {
                // a new fruit for this customer: a new line
                mergedList.add(cartItem);
            }
        }

        return mergedList;
    }

    // Finds the line of one fruit in a list of lines, ignoring the case of the id; null
    // when the fruit is not there.
    private Item findItem(ArrayList<Item> itemList, String fruitId) {
        // look at every line once
        for (Item item : itemList) {
            // same fruit, whatever the case of the id
            if (item.getFruitId().equalsIgnoreCase(fruitId)) {
                return item;
            }
        }

        return null;
    }

    // Copies the lines of a cart or of an order into a DTO and counts the money: the
    // amount of each line, then the total of all of them.
    private OrderResponseDTO convertToOrder(String customerName, ArrayList<Item> itemList) {
        OrderResponseDTO orderDTO = new OrderResponseDTO();
        ArrayList<ItemResponseDTO> rowList = new ArrayList<>();
        ItemResponseDTO row = null;
        double total = 0;

        // one row per line; the total adds up the amounts
        for (Item item : itemList) {
            row = new ItemResponseDTO();
            row.setFruitName(item.getFruitName());
            row.setQuantity(item.getQuantity());
            row.setPrice(item.getPrice());
            row.setAmount(calculateAmount(item));
            rowList.add(row);
            total += row.getAmount();
        }

        orderDTO.setCustomerName(customerName);
        orderDTO.setItemList(rowList);
        orderDTO.setTotal(total);
        return orderDTO;
    }

    // The brief's "Amount" of one line: price x quantity.
    private double calculateAmount(Item item) {
        return item.getPrice() * item.getQuantity();
    }
}
