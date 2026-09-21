package dto;

/**
 * DTO service -> controller -> view: one row of the asset table, a copy of an Asset
 * (the controller never touches the model).
 *
 * @author HE176322
 */
public class AssetDTO {

    // Asset id.
    private String assetId;

    // Name.
    private String name;

    // Color.
    private String color;

    // Price.
    private double price;

    // Weight.
    private double weight;

    // Units in stock.
    private int quantity;

    // JavaBean constructor.
    public AssetDTO() {
    }

    // Returns the asset id.
    public String getAssetId() {
        return assetId;
    }

    // Changes the asset id.
    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    // Returns the name.
    public String getName() {
        return name;
    }

    // Changes the name.
    public void setName(String name) {
        this.name = name;
    }

    // Returns the color.
    public String getColor() {
        return color;
    }

    // Changes the color.
    public void setColor(String color) {
        this.color = color;
    }

    // Returns the price.
    public double getPrice() {
        return price;
    }

    // Changes the price.
    public void setPrice(double price) {
        this.price = price;
    }

    // Returns the weight.
    public double getWeight() {
        return weight;
    }

    // Changes the weight.
    public void setWeight(double weight) {
        this.weight = weight;
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
