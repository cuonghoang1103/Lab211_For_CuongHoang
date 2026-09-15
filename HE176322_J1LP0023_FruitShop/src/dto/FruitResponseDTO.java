package dto;

/**
 * DTO controller -> view: one row of a fruit table.
 *
 * @author HE176322
 */
public class FruitResponseDTO {

    // Item number shown in the first column (1, 2, 3...).
    private int itemNumber;
    // Fruit name.
    private String fruitName;
    // Origin.
    private String origin;
    // Price of one unit.
    private double price;
    // Units in stock (only the owner's table shows it).
    private int quantity;

    // JavaBean constructor.
    public FruitResponseDTO() {
    }

    // Returns the item number.
    public int getItemNumber() {
        return itemNumber;
    }

    // Changes the item number.
    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    // Returns the name.
    public String getFruitName() {
        return fruitName;
    }

    // Changes the name.
    public void setFruitName(String fruitName) {
        this.fruitName = fruitName;
    }

    // Returns the origin.
    public String getOrigin() {
        return origin;
    }

    // Changes the origin.
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    // Returns the price.
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
}
