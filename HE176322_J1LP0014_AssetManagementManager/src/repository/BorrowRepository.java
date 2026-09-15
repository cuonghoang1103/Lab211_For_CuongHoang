package repository;

import constants.Constants;
import model.Borrow;

/**
 * REPOSITORY: borrow.dat - "B001, A001, E160001, 1, 23-12-2021 15:13:46".
 *
 * @author HE176322
 */
public class BorrowRepository extends FileRepository<Borrow> {

    // Creates the store of borrow.dat.
    public BorrowRepository() {
        super(Constants.BORROW_FILE);
    }

    // Five columns: id, asset, employee, quantity, date.
    @Override
    protected Borrow parse(String[] parts) throws Exception {
        // a line with too few or too many columns
        if (parts.length != Constants.TRANSACTION_COLUMNS) {
            throw new Exception();
        }
        return new Borrow(parts[Constants.TRANSACTION_ID], parts[Constants.TRANSACTION_ASSET],
                parts[Constants.TRANSACTION_EMPLOYEE],
                Integer.parseInt(parts[Constants.TRANSACTION_QUANTITY]),
                parts[Constants.TRANSACTION_DATE]);
    }

    // The same five columns, joined with ", ".
    @Override
    protected String format(Borrow borrow) {
        return String.join(Constants.DATA_JOINER, borrow.getId(), borrow.getAssetID(),
                borrow.getEmployeeID(), String.valueOf(borrow.getQuantity()),
                borrow.getDateTime());
    }
}
