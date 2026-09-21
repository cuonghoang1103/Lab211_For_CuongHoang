package model;

/**
 * MODEL: one line of a cart or of an order - which fruit, how many, at what price. The
 * amount of the line (price x quantity) is counted by the service, not here.
 *
 * @author HE176322
 */
public class Item {

    // Id of the fruit bought, to find it again in the shop.
    private String fruitId;

    // Name of the fruit, kept so the order still reads well later.
    private String fruitName;

    // Price of one unit at the moment of buying.
    private double price;

    // Units bought.
    private int quantity;

    // JavaBean constructor: an empty line, filled through the setters.
    public Item() {
    }

    // Creates a line with every field filled in.
    public Item(String fruitId, String fruitName, double price, int quantity) {
        this.fruitId = fruitId;
        this.fruitName = fruitName;
        this.price = price;
        this.quantity = quantity;
    }

    // Returns the fruit id.
    public String getFruitId() {
        return fruitId;
    }

    // Changes the fruit id.
    public void setFruitId(String fruitId) {
        this.fruitId = fruitId;
    }

    // Returns the fruit name.
    public String getFruitName() {
        return fruitName;
    }

    // Changes the fruit name.
    public void setFruitName(String fruitName) {
        this.fruitName = fruitName;
    }

    // Returns the price of one unit.
    public double getPrice() {
        return price;
    }

    // Changes the price.
    public void setPrice(double price) {
        this.price = price;
    }

    // Returns the units bought.
    public int getQuantity() {
        return quantity;
    }

    // Changes the units bought.
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
