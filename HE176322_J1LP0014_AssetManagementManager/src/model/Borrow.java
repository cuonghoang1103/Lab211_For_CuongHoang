package model;

/**
 * MODEL: one row of borrow.dat - units that left the stock and are held by an employee.
 *
 * @author HE176322
 */
public class Borrow extends Transaction {

    // JavaBean constructor.
    public Borrow() {
    }

    // Creates a borrow with every field filled in.
    public Borrow(String bID, String assetID, String employeeID, int quantity,
            String borrowDateTime) {
        super(bID, assetID, employeeID, quantity, borrowDateTime);
    }
}
