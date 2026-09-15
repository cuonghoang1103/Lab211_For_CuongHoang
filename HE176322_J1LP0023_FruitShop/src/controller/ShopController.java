package controller;

import constants.Message;
import dto.FruitRequestDTO;
import dto.FruitResponseDTO;
import dto.OrderRequestDTO;
import java.util.ArrayList;
import repository.FruitRepository;
import repository.OrderRepository;
import service.FruitService;
import service.OrderService;
import view.ShopView;

/**
 * CONTROLLER (and Facade): receives a request DTO from main, asks a service to do the
 * work, and hands the result to the view.
 *
 * @author HE176322
 */
public class ShopController {

    // Rules of the shop owner: create and list fruits.
    private FruitService fruitService;
    // Rules of the buyer: cart, stock check, orders.
    private OrderService orderService;
    // Prints every result.
    private ShopView shopView;

    // Wires the program: ONE fruit store shared by both services, so a sale lowers the
    // stock the owner sees.
    public ShopController() {
        FruitRepository fruitRepository = new FruitRepository();
        fruitService = new FruitService(fruitRepository);
        orderService = new OrderService(fruitRepository, new OrderRepository());
        shopView = new ShopView();
    }

    // Create fruit, first step: refuses an id already used.
    public void checkFruitId(FruitRequestDTO requestDTO) throws Exception {
        fruitService.checkFruitId(requestDTO);
    }

    // Create fruit: stores it and shows "Fruit F001 has been created.".
    public void createFruit(FruitRequestDTO requestDTO) throws Exception {
        String fruitId = fruitService.createFruit(requestDTO);
        shopView.showMessage(String.format(Message.CREATE_SUCCESS, fruitId));
    }

    // Shows every fruit with its quantity in stock (after the owner answers N).
    public void displayStock() {
        shopView.displayStock(fruitService.getAllFruits());
    }

    // View orders: every customer with the items bought and the total.
    public void viewOrders() throws Exception {
        shopView.displayOrders(orderService.getAllOrders());
    }

    // Shopping, first step: refuses when the shop has no fruit, empties the cart.
    public void startShopping() throws Exception {
        orderService.startShopping();
    }

    // Shows the buyer's "List of Fruit" and returns how many items it has.
    public int displayFruitList() {
        ArrayList<FruitResponseDTO> fruits = fruitService.getAllFruits();
        shopView.displayFruitList(fruits);
        return fruits.size();
    }

    // Shows "You selected: Coconut" for the chosen item.
    public void selectFruit(OrderRequestDTO requestDTO) {
        String fruitName = orderService.selectFruit(requestDTO);
        shopView.showMessage(String.format(Message.SELECTED, fruitName));
    }

    // Puts the chosen quantity in the cart; refuses more than the stock left.
    public void addToCart(OrderRequestDTO requestDTO) throws Exception {
        orderService.addToCart(requestDTO);
    }

    // Shows the cart with its total, before the name is asked.
    public void displayCart() {
        shopView.displayCart(orderService.getCart());
    }

    // Saves the order and thanks the customer.
    public void placeOrder(OrderRequestDTO requestDTO) {
        String customerName = orderService.placeOrder(requestDTO);
        shopView.showMessage(String.format(Message.ORDER_SUCCESS, customerName));
    }

    // Leaves the shopping screen; a non-empty cart is thrown away with a message.
    public void cancelShopping() {
        // only a cart that had fruits is worth a message
        if (orderService.cancelShopping()) {
            shopView.showMessage(Message.ORDER_CANCELLED);
        }
    }
}
