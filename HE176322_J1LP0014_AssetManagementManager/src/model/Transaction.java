package model;

/**
 * MODEL: what a request and a borrow have in common - id, asset, employee, quantity and
 * date. Abstract: every row is a Request or a Borrow. Read only: a request is approved
 * or cancelled, never edited.
 *
 * @author HE176322
 */
public abstract class Transaction implements Identifiable {

    // R001 or B001.
    private String id;
    // Asset asked for or borrowed.
    private String assetID;
    // Employee who asked or borrowed.
    private String employeeID;
    // Units.
    private int quantity;
    // Date and time as the file holds it: 23-12-2021 13:17:56.
    private String dateTime;

    // JavaBean constructor; only subclasses call it.
    protected Transaction() {
    }

    // Creates a row with every field filled in.
    protected Transaction(String id, String assetID, String employeeID, int quantity,
            String dateTime) {
        this.id = id;
        this.assetID = assetID;
        this.employeeID = employeeID;
        this.quantity = quantity;
        this.dateTime = dateTime;
    }

    // The key of request.dat or borrow.dat.
    @Override
    public String getId() {
        return id;
    }

    // Returns the asset id.
    public String getAssetID() {
        return assetID;
    }

    // Returns the employee id.
    public String getEmployeeID() {
        return employeeID;
    }

    // Returns the units.
    public int getQuantity() {
        return quantity;
    }

    // Returns the date and time.
    public String getDateTime() {
        return dateTime;
    }
}
