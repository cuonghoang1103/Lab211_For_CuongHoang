package dto;

/**
 * DTO carrying what the user typed FROM main INTO the controller: the bills and the
 * amount in the wallet.
 *
 * @author HE176322
 */
public class BillRequestDTO {

    // Values of the bills, already validated by main.
    private int[] bills;
    // Amount in the wallet, already validated by main.
    private int walletAmount;

    // Creates an empty request; main fills it through the setters.
    public BillRequestDTO() {
    }

    // Returns the bills.
    public int[] getBills() {
        return bills;
    }

    // Sets the bills.
    public void setBills(int[] bills) {
        this.bills = bills;
    }

    // Returns the wallet amount.
    public int getWalletAmount() {
        return walletAmount;
    }

    // Sets the wallet amount.
    public void setWalletAmount(int walletAmount) {
        this.walletAmount = walletAmount;
    }
}
