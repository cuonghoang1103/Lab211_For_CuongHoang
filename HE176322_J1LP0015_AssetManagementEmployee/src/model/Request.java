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

    // brief: rID, requestDateTime - creates a request with every field filled in.
    public Request(String requestId, String assetId, String employeeId, int quantity,
            String requestDateTime) {
        super(requestId, assetId, employeeId, quantity, requestDateTime);
    }
}
