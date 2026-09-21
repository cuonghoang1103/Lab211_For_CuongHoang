package repository;

import constants.Constants;
import model.Borrow;

/**
 * REPOSITORY: borrow.dat - "B001, A001, E160001, 1, 23-12-2021 15:13:46".
 *
 * @author HE176322
 */
public class BorrowRepository extends FileRepository<Borrow> {

    // Creates the store of borrow.dat: five columns.
    public BorrowRepository() {
        super(Constants.BORROW_FILE, Constants.TRANSACTION_COLUMNS);
    }

    // Five columns: id, asset, employee, quantity, date.
    @Override
    protected Borrow parse(String[] partArray) {
        return new Borrow(partArray[Constants.TRANSACTION_ID],
                partArray[Constants.TRANSACTION_ASSET],
                partArray[Constants.TRANSACTION_EMPLOYEE],
                Integer.parseInt(partArray[Constants.TRANSACTION_QUANTITY]),
                partArray[Constants.TRANSACTION_DATE]);
    }

    // The same five columns, joined with ", ".
    @Override
    protected String format(Borrow borrow) {
        return String.join(Constants.DATA_JOINER, borrow.getId(), borrow.getAssetId(),
                borrow.getEmployeeId(), String.valueOf(borrow.getQuantity()),
                borrow.getDateTime());
    }
}
