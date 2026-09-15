package dto;

/**
 * DTO controller -> view: one line of a cart or of an order.
 *
 * @author HE176322
 */
public class ItemResponseDTO {

    // Fruit name.
    private String fruitName;
    // Units bought.
    private int quantity;
    // Price of one unit.
    private double price;
    // Price x quantity.
    private double amount;

    // JavaBean constructor.
    public ItemResponseDTO() {
    }

    // Returns the fruit name.
    public String getFruitName() {
        return fruitName;
    }

    // Changes the fruit name.
    public void setFruitName(String fruitName) {
        this.fruitName = fruitName;
    }

    // Returns the units bought.
    public int getQuantity() {
        return quantity;
    }

    // Changes the units bought.
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Returns the price.
    public double getPrice() {
        return price;
    }

    // Changes the price.
    public void setPrice(double price) {
        this.price = price;
    }

    // Returns the amount.
    public double getAmount() {
        return amount;
    }

    // Changes the amount.
    public void setAmount(double amount) {
        this.amount = amount;
    }
}
