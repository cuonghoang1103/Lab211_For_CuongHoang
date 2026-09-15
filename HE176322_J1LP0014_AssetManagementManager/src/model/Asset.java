package model;

/**
 * MODEL: one row of asset.dat. The asset id has no setter: request.dat and borrow.dat
 * point at it, so it cannot change after created (the brief's Function 0).
 *
 * @author HE176322
 */
public class Asset implements Identifiable {

    // Asset id, e.g. A001 - read only.
    private String assetID;
    // Name, e.g. Samsung projector.
    private String name;
    // Color.
    private String color;
    // Price, greater than 0.
    private double price;
    // Weight, greater than 0.
    private double weight;
    // Units in stock, 0 or more.
    private int quantity;

    // JavaBean constructor.
    public Asset() {
    }

    // Creates an asset with every field filled in.
    public Asset(String assetID, String name, String color, double price, double weight,
            int quantity) {
        this.assetID = assetID;
        this.name = name;
        this.color = color;
        this.price = price;
        this.weight = weight;
        this.quantity = quantity;
    }

    // The key of asset.dat.
    @Override
    public String getId() {
        return assetID;
    }

    // Returns the asset id.
    public String getAssetID() {
        return assetID;
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
