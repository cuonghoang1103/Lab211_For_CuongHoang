package dto;

/**
 * DTO service -> controller -> view: one row of this employee's requests or borrows, with
 * the asset name looked up.
 *
 * @author HE176322
 */
public class TransactionDTO {

    // Request or borrow id.
    private String id;

    // Asset id.
    private String assetId;

    // Asset name.
    private String assetName;

    // Units.
    private int quantity;

    // Date and time.
    private String dateTime;

    // JavaBean constructor.
    public TransactionDTO() {
    }

    // Returns the id.
    public String getId() {
        return id;
    }

    // Changes the id.
    public void setId(String id) {
        this.id = id;
    }

    // Returns the asset id.
    public String getAssetId() {
        return assetId;
    }

    // Changes the asset id.
    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    // Returns the asset name.
    public String getAssetName() {
        return assetName;
    }

    // Changes the asset name.
    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    // Returns the units.
    public int getQuantity() {
        return quantity;
    }

    // Changes the units.
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Returns the date and time.
    public String getDateTime() {
        return dateTime;
    }

    // Changes the date and time.
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
