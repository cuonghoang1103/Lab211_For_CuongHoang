package view;

import constants.Constants;
import constants.Message;
import dto.AssetResponseDTO;
import dto.TransactionResponseDTO;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

/**
 * VIEW: the only place (with main) allowed to print results.
 *
 * @author HE176322
 */
public class AssetView {

    // Two decimals with a dot on every machine's locale: 500.00.
    private DecimalFormat decimalFormat = new DecimalFormat(Constants.DECIMAL_PATTERN,
            DecimalFormatSymbols.getInstance(Locale.US));

    // Prints a table of assets.
    public void displayAssets(ArrayList<AssetResponseDTO> assets) {
        System.out.println(String.format(Constants.ASSET_ROW, Message.COL_ID, Message.COL_NAME,
                Message.COL_COLOR, Message.COL_PRICE, Message.COL_WEIGHT,
                Message.COL_QUANTITY));
        System.out.println(Constants.TABLE_LINE);
        // one row per asset
        for (AssetResponseDTO asset : assets) {
            System.out.println(String.format(Constants.ASSET_ROW, asset.getAssetID(),
                    asset.getName(), asset.getColor(), decimalFormat.format(asset.getPrice()),
                    decimalFormat.format(asset.getWeight()), asset.getQuantity()));
        }
        System.out.println(Constants.TABLE_LINE);
    }

    // Prints one asset as a one-row table.
    public void displayAsset(AssetResponseDTO asset) {
        ArrayList<AssetResponseDTO> one = new ArrayList<>();
        one.add(asset);
        displayAssets(one);
    }

    // Prints the waiting requests.
    public void displayRequests(ArrayList<TransactionResponseDTO> requests) {
        displayTransactions(requests, Message.COL_REQUESTED_AT);
    }

    // Prints the borrows.
    public void displayBorrows(ArrayList<TransactionResponseDTO> borrows) {
        displayTransactions(borrows, Message.COL_BORROWED_AT);
    }

    // Prints a one-line result such as "Successfully".
    public void showMessage(String message) {
        System.out.println(message);
    }

    // One table for requests and borrows; only the date heading differs.
    private void displayTransactions(ArrayList<TransactionResponseDTO> rows, String dateLabel) {
        System.out.println(String.format(Constants.TRANSACTION_ROW, Message.COL_ID,
                Message.COL_ASSET, Message.COL_ASSET_NAME, Message.COL_EMPLOYEE,
                Message.COL_EMPLOYEE_NAME, Message.COL_QUANTITY, dateLabel));
        System.out.println(Constants.TABLE_LINE);
        // one row per request or borrow
        for (TransactionResponseDTO row : rows) {
            System.out.println(String.format(Constants.TRANSACTION_ROW, row.getId(),
                    row.getAssetID(), row.getAssetName(), row.getEmployeeID(),
                    row.getEmployeeName(), row.getQuantity(), row.getDateTime()));
        }
        System.out.println(Constants.TABLE_LINE);
    }
}
