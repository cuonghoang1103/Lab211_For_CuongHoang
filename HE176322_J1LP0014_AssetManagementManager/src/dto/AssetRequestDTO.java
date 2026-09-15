package dto;

/**
 * DTO main -> controller: what the manager typed about an asset. Wrapper types because
 * null means "left blank, keep the old value" in update.
 *
 * @author HE176322
 */
public class AssetRequestDTO {

    // Asset id.
    private String assetID;
    // Name, or null to keep.
    private String name;
    // Color, or null to keep.
    private String color;
    // Price, or null to keep.
    private Double price;
    // Weight, or null to keep.
    private Double weight;
    // Quantity, or null to keep.
    private Integer quantity;
    // Text searched in the names.
    private String keyword;

    // JavaBean constructor.
    public AssetRequestDTO() {
    }

    // Returns the asset id.
    public String getAssetID() {
        return assetID;
    }

    // Changes the asset id.
    public void setAssetID(String assetID) {
        this.assetID = assetID;
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
    public Double getPrice() {
        return price;
    }

    // Changes the price.
    public void setPrice(Double price) {
        this.price = price;
    }

    // Returns the weight.
    public Double getWeight() {
        return weight;
    }

    // Changes the weight.
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    // Returns the quantity.
    public Integer getQuantity() {
        return quantity;
    }

    // Changes the quantity.
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    // Returns the search text.
    public String getKeyword() {
        return keyword;
    }

    // Changes the search text.
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
