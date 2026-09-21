package controller;

import dto.FruitRequestDTO;
import dto.OrderRequestDTO;
import dto.ShopResponseDTO;
import repository.FruitRepository;
import repository.OrderRepository;
import service.FruitService;
import service.OrderService;
import view.ShopView;

/**
 * CONTROLLER (and Facade): receives a request DTO from main, asks a service to do the
 * work, and hands the answer to the view - one render per flow. No Scanner, no print, no
 * model; a broken rule is thrown as an Exception(Message.X) for main to print.
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

    // Option 1, check only (no render, nothing stored): an id already used is thrown
    // ("Fruit ID F001 already exists."), so main asks the id again at once.
    public void checkFruitId(FruitRequestDTO requestDTO) throws Exception {
        fruitService.checkFruitId(requestDTO);
    }

    // Option 1, one fruit: stores it, then the view prints "Fruit F001 has been created."
    // - once.
    public void createFruit(FruitRequestDTO requestDTO) throws Exception {
        ShopResponseDTO responseDTO = fruitService.createFruit(requestDTO);

        // one render for the whole flow
        shopView.setResponseDTO(responseDTO);
        shopView.display();
    }

    // Option 1, the answer N: the brief's "display all Fruits what are created" - every
    // fruit with its quantity in stock - once.
    public void displayFruits() {
        ShopResponseDTO responseDTO = fruitService.getStock();

        // one render for the whole flow
        shopView.setResponseDTO(responseDTO);
        shopView.display();
    }

    // Option 2: every order, customer by customer - once; nobody has ordered yet is
    // thrown.
    public void displayOrders() throws Exception {
        ShopResponseDTO responseDTO = orderService.getAllOrders();

        // one render for the whole flow
        shopView.setResponseDTO(responseDTO);
        shopView.display();
    }

    // Option 3, a round begins: the buyer's "List of Fruit" - once; an empty shop is
    // thrown. Answers with the number of fruits listed, so main knows the legal items
    // (0..that number) and can ask the item again at once.
    public int displayFruitList() throws Exception {
        ShopResponseDTO responseDTO = fruitService.getFruitList();

        // one render for the whole flow
        shopView.setResponseDTO(responseDTO);
        shopView.display();
        return responseDTO.getFruitList().size();
    }

    // Option 3, an item chosen: "You selected: Coconut" - once.
    public void selectFruit(OrderRequestDTO requestDTO) {
        ShopResponseDTO responseDTO = orderService.selectFruit(requestDTO);

        // one render for the whole flow
        shopView.setResponseDTO(responseDTO);
        shopView.display();
    }

    // Option 3, a quantity typed: puts it in the cart; more than the stock left is thrown
    // ("Only 5 Orange left in stock."). Nothing to render: the brief's question is next.
    public void addToCart(OrderRequestDTO requestDTO) throws Exception {
        orderService.addToCart(requestDTO);
    }

    // Option 3, the answer Y: the cart with its total - once.
    public void displayCart() {
        ShopResponseDTO responseDTO = orderService.getCart();

        // one render for the whole flow
        shopView.setResponseDTO(responseDTO);
        shopView.display();
    }

    // Option 3, the name typed: saves the order, then "Thank you ..." - once.
    public void saveOrder(OrderRequestDTO requestDTO) {
        ShopResponseDTO responseDTO = orderService.saveOrder(requestDTO);

        // one render for the whole flow
        shopView.setResponseDTO(responseDTO);
        shopView.display();
    }

    // Option 3, item 0: leaves the shop; a cart with fruits is thrown away and the view
    // says so - once (an empty cart prints nothing).
    public void cancelShopping() {
        ShopResponseDTO responseDTO = orderService.cancelShopping();

        // one render for the whole flow
        shopView.setResponseDTO(responseDTO);
        shopView.display();
    }
}
