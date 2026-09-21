package view;

import constants.Constants;
import constants.Message;
import dto.AssetDTO;
import dto.AssetResponseDTO;
import dto.TransactionDTO;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

/**
 * VIEW: the only place (with main) allowed to print results. It receives the data through
 * its attribute (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class AssetView {

    // Two decimals with a dot on every machine's locale: 500.00.
    private DecimalFormat decimalFormat = new DecimalFormat(Constants.DECIMAL_PATTERN,
            DecimalFormatSymbols.getInstance(Locale.US));

    // The answer to print, handed over by the controller.
    private AssetResponseDTO responseDTO;

    // Receives the answer the next display() call will print.
    public void setResponseDTO(AssetResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints what the controller set: the result line first, then the table.
    public void display() {
        // login, create and update answer with a line
        if (responseDTO.getMessage() != null) {
            System.out.println(responseDTO.getMessage());
        }

        // search, create and update show assets
        if (responseDTO.getAssetList() != null) {
            displayAssets();
        }

        // Function 5 shows the waiting requests
        if (responseDTO.getRequestList() != null) {
            displayTransactions(responseDTO.getRequestList(), Message.COL_REQUESTED_AT);
        }

        // Function 6 shows the borrows
        if (responseDTO.getBorrowList() != null) {
            displayTransactions(responseDTO.getBorrowList(), Message.COL_BORROWED_AT);
        }
    }

    // Prints the asset table of the answer.
    private void displayAssets() {
        System.out.println(String.format(Constants.ASSET_ROW, Message.COL_ID, Message.COL_NAME,
                Message.COL_COLOR, Message.COL_PRICE, Message.COL_WEIGHT,
                Message.COL_QUANTITY));
        System.out.println(Constants.TABLE_LINE);

        // one row per asset
        for (AssetDTO assetDTO : responseDTO.getAssetList()) {
            System.out.println(String.format(Constants.ASSET_ROW, assetDTO.getAssetId(),
                    assetDTO.getName(), assetDTO.getColor(),
                    decimalFormat.format(assetDTO.getPrice()),
                    decimalFormat.format(assetDTO.getWeight()), assetDTO.getQuantity()));
        }

        System.out.println(Constants.TABLE_LINE);
    }

    // One table for requests and borrows (both rows of the answer); only the date heading
    // differs.
    private void displayTransactions(ArrayList<TransactionDTO> transactionList,
            String dateLabel) {
        System.out.println(String.format(Constants.TRANSACTION_ROW, Message.COL_ID,
                Message.COL_ASSET, Message.COL_ASSET_NAME, Message.COL_EMPLOYEE,
                Message.COL_EMPLOYEE_NAME, Message.COL_QUANTITY, dateLabel));
        System.out.println(Constants.TABLE_LINE);

        // one row per request or borrow
        for (TransactionDTO transactionDTO : transactionList) {
            System.out.println(String.format(Constants.TRANSACTION_ROW, transactionDTO.getId(),
                    transactionDTO.getAssetId(), transactionDTO.getAssetName(),
                    transactionDTO.getEmployeeId(), transactionDTO.getEmployeeName(),
                    transactionDTO.getQuantity(), transactionDTO.getDateTime()));
        }

        System.out.println(Constants.TABLE_LINE);
    }
}
