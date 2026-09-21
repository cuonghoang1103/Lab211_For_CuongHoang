package dto;

import java.util.ArrayList;

/**
 * DTO controller -> view: everything ONE flow shows, handed to the view through its
 * attribute (checklist 1.1). Each part is null when the flow has nothing of it; the view
 * prints only the parts that are set.
 *
 * @author HE176322
 */
public class ShopResponseDTO {

    // The one-line result, e.g. "Fruit F001 has been created.", or null.
    private String message;

    // The owner's table (every fruit with its quantity in stock), or null.
    private ArrayList<FruitResponseDTO> stockList;

    // The buyer's "List of Fruit" (item, name, origin, price), or null.
    private ArrayList<FruitResponseDTO> fruitList;

    // The cart with its total, shown before the name is asked, or null.
    private OrderResponseDTO cart;

    // Every saved order, customer by customer, or null.
    private ArrayList<OrderResponseDTO> orderList;

    // JavaBean constructor: an empty answer, filled through the setters.
    public ShopResponseDTO() {
    }

    // Returns the one-line result.
    public String getMessage() {
        return message;
    }

    // Changes the one-line result.
    public void setMessage(String message) {
        this.message = message;
    }

    // Returns the owner's table.
    public ArrayList<FruitResponseDTO> getStockList() {
        return stockList;
    }

    // Changes the owner's table.
    public void setStockList(ArrayList<FruitResponseDTO> stockList) {
        this.stockList = stockList;
    }

    // Returns the buyer's list.
    public ArrayList<FruitResponseDTO> getFruitList() {
        return fruitList;
    }

    // Changes the buyer's list.
    public void setFruitList(ArrayList<FruitResponseDTO> fruitList) {
        this.fruitList = fruitList;
    }

    // Returns the cart.
    public OrderResponseDTO getCart() {
        return cart;
    }

    // Changes the cart.
    public void setCart(OrderResponseDTO cart) {
        this.cart = cart;
    }

    // Returns the saved orders.
    public ArrayList<OrderResponseDTO> getOrderList() {
        return orderList;
    }

    // Changes the saved orders.
    public void setOrderList(ArrayList<OrderResponseDTO> orderList) {
        this.orderList = orderList;
    }
}
