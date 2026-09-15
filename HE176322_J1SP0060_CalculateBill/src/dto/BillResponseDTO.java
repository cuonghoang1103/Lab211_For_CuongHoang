package dto;

/**
 * DTO carrying the result FROM the controller OUT TO the view: the total of the bills and
 * whether the wallet can pay it.
 *
 * @author HE176322
 */
public class BillResponseDTO {

    // Total of all bills (the result of calcTotal).
    private int total;
    // True when the wallet holds at least the total (payMoney).
    private boolean canBuy;

    // Creates an empty response (JavaBean constructor).
    public BillResponseDTO() {
    }

    // Creates the response with both values.
    public BillResponseDTO(int total, boolean canBuy) {
        this.total = total;
        this.canBuy = canBuy;
    }

    // Returns the total.
    public int getTotal() {
        return total;
    }

    // Sets the total.
    public void setTotal(int total) {
        this.total = total;
    }

    // Returns the payment status.
    public boolean isCanBuy() {
        return canBuy;
    }

    // Sets the payment status.
    public void setCanBuy(boolean canBuy) {
        this.canBuy = canBuy;
    }
}
