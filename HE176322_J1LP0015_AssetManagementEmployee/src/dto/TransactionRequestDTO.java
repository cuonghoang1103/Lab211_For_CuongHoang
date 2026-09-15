package dto;

/**
 * DTO main -> controller: a request to send, or the request / borrow id chosen. The
 * employee id is filled by the controller from the session, never typed.
 *
 * @author HE176322
 */
public class TransactionRequestDTO {

    // Request or borrow id typed (cancel, return).
    private String id;
    // Asset to borrow.
    private String assetID;
    // Quantity to borrow.
    private int quantity;
    // Employee logged in.
    private String employeeID;

    // JavaBean constructor.
    public TransactionRequestDTO() {
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
    public String getAssetID() {
        return assetID;
    }

    // Changes the asset id.
    public void setAssetID(String assetID) {
        this.assetID = assetID;
    }

    // Returns the quantity.
    public int getQuantity() {
        return quantity;
    }

    // Changes the quantity.
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Returns the employee id.
    public String getEmployeeID() {
        return employeeID;
    }

    // Changes the employee id.
    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }
}
