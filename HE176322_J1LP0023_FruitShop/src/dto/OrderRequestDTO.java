package dto;

/**
 * DTO main -> controller: what the buyer typed while shopping.
 *
 * @author HE176322
 */
public class OrderRequestDTO {

    // Item number chosen in the fruit list.
    private int itemNumber;
    // Quantity to buy.
    private int quantity;
    // Name typed to finish the order.
    private String customerName;

    // JavaBean constructor.
    public OrderRequestDTO() {
    }

    // Returns the item number.
    public int getItemNumber() {
        return itemNumber;
    }

    // Changes the item number.
    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    // Returns the quantity to buy.
    public int getQuantity() {
        return quantity;
    }

    // Changes the quantity to buy.
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Returns the customer name.
    public String getCustomerName() {
        return customerName;
    }

    // Changes the customer name.
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
