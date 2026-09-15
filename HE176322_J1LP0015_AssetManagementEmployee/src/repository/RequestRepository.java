package repository;

import constants.Constants;
import model.Request;

/**
 * REPOSITORY: request.dat - "R001, A001, E140449, 1, 23-12-2021 13:17:56".
 *
 * @author HE176322
 */
public class RequestRepository extends FileRepository<Request> {

    // Creates the store of request.dat.
    public RequestRepository() {
        super(Constants.REQUEST_FILE);
    }

    // Five columns: id, asset, employee, quantity, date.
    @Override
    protected Request parse(String[] parts) throws Exception {
        // a line with too few or too many columns
        if (parts.length != Constants.TRANSACTION_COLUMNS) {
            throw new Exception();
        }
        return new Request(parts[Constants.TRANSACTION_ID], parts[Constants.TRANSACTION_ASSET],
                parts[Constants.TRANSACTION_EMPLOYEE],
                Integer.parseInt(parts[Constants.TRANSACTION_QUANTITY]),
                parts[Constants.TRANSACTION_DATE]);
    }

    // The same five columns, joined with ", ".
    @Override
    protected String format(Request request) {
        return String.join(Constants.DATA_JOINER, request.getId(), request.getAssetID(),
                request.getEmployeeID(), String.valueOf(request.getQuantity()),
                request.getDateTime());
    }
}
