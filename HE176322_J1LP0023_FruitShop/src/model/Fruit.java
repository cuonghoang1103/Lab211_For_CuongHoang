package model;

/**
 * MODEL: one fruit of the shop, with the five attributes of the brief.
 *
 * @author HE176322
 */
public class Fruit {

    // Unique id, e.g. F001.
    private String fruitId;
    // Name shown to the buyer.
    private String fruitName;
    // Price of one unit, greater than 0.
    private double price;
    // Units still in stock, never below 0.
    private int quantity;
    // Country the fruit comes from.
    private String origin;

    // JavaBean constructor: an empty fruit, filled through the setters.
    public Fruit() {
    }

    // Creates a fruit with every field filled in.
    public Fruit(String fruitId, String fruitName, double price, int quantity,
            String origin) {
        this.fruitId = fruitId;
        this.fruitName = fruitName;
        this.price = price;
        this.quantity = quantity;
        this.origin = origin;
    }

    // Returns the id.
    public String getFruitId() {
        return fruitId;
    }

    // Changes the id.
    public void setFruitId(String fruitId) {
        this.fruitId = fruitId;
    }

    // Returns the name.
    public String getFruitName() {
        return fruitName;
    }

    // Changes the name.
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

    // Returns the units in stock.
    public int getQuantity() {
        return quantity;
    }

    // Changes the units in stock.
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Returns the origin.
    public String getOrigin() {
        return origin;
    }

    // Changes the origin.
    public void setOrigin(String origin) {
        this.origin = origin;
    }
}
