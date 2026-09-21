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

    // brief: bID, borrowDateTime - creates a borrow with every field filled in.
    public Borrow(String borrowId, String assetId, String employeeId, int quantity,
            String borrowDateTime) {
        super(borrowId, assetId, employeeId, quantity, borrowDateTime);
    }
}
