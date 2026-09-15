package model;

/**
 * MODEL: one row of request.dat - an asset an employee asked for, not approved yet.
 *
 * @author HE176322
 */
public class Request extends Transaction {

    // JavaBean constructor.
    public Request() {
    }

    // Creates a request with every field filled in.
    public Request(String rID, String assetID, String employeeID, int quantity,
            String requestDateTime) {
        super(rID, assetID, employeeID, quantity, requestDateTime);
    }
}
