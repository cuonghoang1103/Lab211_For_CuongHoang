package dto;

import constants.SalaryStatus;

/**
 * DTO carrying one salary adjustment FROM main INTO the controller (options 2 and 3).
 *
 * @author HE176322
 */
public class SalaryRequestDTO {

    // UP for option 2, DOWN for option 3; set by main, not typed.
    private SalaryStatus status;
    // Code of the worker to adjust, typed by the user.
    private String code;
    // Amount of money to add or subtract, typed by the user.
    private double amount;

    // Creates an empty request; main fills it through the setters.
    public SalaryRequestDTO() {
    }

    // Returns the direction of the adjustment.
    public SalaryStatus getStatus() {
        return status;
    }

    // Sets the direction of the adjustment.
    public void setStatus(SalaryStatus status) {
        this.status = status;
    }

    // Returns the worker code.
    public String getCode() {
        return code;
    }

    // Sets the worker code.
    public void setCode(String code) {
        this.code = code;
    }

    // Returns the amount.
    public double getAmount() {
        return amount;
    }

    // Sets the amount.
    public void setAmount(double amount) {
        this.amount = amount;
    }
}
