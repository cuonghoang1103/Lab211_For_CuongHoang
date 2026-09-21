package dto;

import java.util.ArrayList;

/**
 * DTO controller -> view: a whole cart or a whole saved order - its lines and its total.
 *
 * @author HE176322
 */
public class OrderResponseDTO {

    // Customer name (null for the cart, which has no owner yet).
    private String customerName;

    // The lines, in the order the fruits were chosen.
    private ArrayList<ItemResponseDTO> itemList;

    // Sum of the amounts.
    private double total;

    // JavaBean constructor.
    public OrderResponseDTO() {
    }

    // Returns the customer name.
    public String getCustomerName() {
        return customerName;
    }

    // Changes the customer name.
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    // Returns the lines.
    public ArrayList<ItemResponseDTO> getItemList() {
        return itemList;
    }

    // Changes the lines.
    public void setItemList(ArrayList<ItemResponseDTO> itemList) {
        this.itemList = itemList;
    }

    // Returns the total.
    public double getTotal() {
        return total;
    }

    // Changes the total.
    public void setTotal(double total) {
        this.total = total;
    }
}
