package repository;

import constants.Constants;
import model.Request;

/**
 * REPOSITORY: request.dat - "R001, A001, E140449, 1, 23-12-2021 13:17:56".
 *
 * @author HE176322
 */
public class RequestRepository extends FileRepository<Request> {

    // Creates the store of request.dat: five columns.
    public RequestRepository() {
        super(Constants.REQUEST_FILE, Constants.TRANSACTION_COLUMNS);
    }

    // Five columns: id, asset, employee, quantity, date.
    @Override
    protected Request parse(String[] partArray) {
        return new Request(partArray[Constants.TRANSACTION_ID],
                partArray[Constants.TRANSACTION_ASSET],
                partArray[Constants.TRANSACTION_EMPLOYEE],
                Integer.parseInt(partArray[Constants.TRANSACTION_QUANTITY]),
                partArray[Constants.TRANSACTION_DATE]);
    }

    // The same five columns, joined with ", ".
    @Override
    protected String format(Request request) {
        return String.join(Constants.DATA_JOINER, request.getId(), request.getAssetId(),
                request.getEmployeeId(), String.valueOf(request.getQuantity()),
                request.getDateTime());
    }
}
