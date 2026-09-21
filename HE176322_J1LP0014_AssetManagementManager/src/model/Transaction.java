package model;

/**
 * MODEL: what a request and a borrow have in common - id, asset, employee, quantity and
 * date. Abstract: every row is a Request or a Borrow. Read only: a request is approved
 * or cancelled, never edited.
 *
 * @author HE176322
 */
public abstract class Transaction implements IRecord {

    // brief: rID / bID - R001 or B001.
    private String id;

    // brief: assetID - the asset asked for or borrowed.
    private String assetId;

    // brief: employeeID - the employee who asked or borrowed.
    private String employeeId;

    // Units.
    private int quantity;

    // Date and time as the file holds it: 23-12-2021 13:17:56.
    private String dateTime;

    // JavaBean constructor; only subclasses call it.
    protected Transaction() {
    }

    // Creates a row with every field filled in.
    protected Transaction(String id, String assetId, String employeeId, int quantity,
            String dateTime) {
        this.id = id;
        this.assetId = assetId;
        this.employeeId = employeeId;
        this.quantity = quantity;
        this.dateTime = dateTime;
    }

    // The key of request.dat or borrow.dat.
    @Override
    public String getId() {
        return id;
    }

    // Returns the asset id.
    public String getAssetId() {
        return assetId;
    }

    // Returns the employee id.
    public String getEmployeeId() {
        return employeeId;
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
