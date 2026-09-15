package dto;

/**
 * DTO main -> controller: the five fields typed for a new fruit.
 *
 * @author HE176322
 */
public class FruitRequestDTO {

    // Fruit id typed.
    private String fruitId;
    // Fruit name typed.
    private String fruitName;
    // Price typed.
    private double price;
    // Quantity in stock typed.
    private int quantity;
    // Origin typed.
    private String origin;

    // JavaBean constructor.
    public FruitRequestDTO() {
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

    // Returns the price.
    public double getPrice() {
        return price;
    }

    // Changes the price.
    public void setPrice(double price) {
        this.price = price;
    }

    // Returns the quantity in stock.
    public int getQuantity() {
        return quantity;
    }

    // Changes the quantity in stock.
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
