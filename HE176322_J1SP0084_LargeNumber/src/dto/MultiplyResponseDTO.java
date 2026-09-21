package dto;

/**
 * DTO carrying the result FROM the controller OUT TO the view: the two numbers and their
 * product, as text without leading zeros.
 *
 * @author HE176322
 */
public class MultiplyResponseDTO {

    // The first number, leading zeros removed.
    private String firstNumber;

    // The second number, leading zeros removed.
    private String secondNumber;

    // The product, leading zeros removed.
    private String product;

    // JavaBean constructor: an empty response, filled through the setters.
    public MultiplyResponseDTO() {
    }

    // Returns the first number.
    public String getFirstNumber() {
        return firstNumber;
    }

    // Sets the first number.
    public void setFirstNumber(String firstNumber) {
        this.firstNumber = firstNumber;
    }

    // Returns the second number.
    public String getSecondNumber() {
        return secondNumber;
    }

    // Sets the second number.
    public void setSecondNumber(String secondNumber) {
        this.secondNumber = secondNumber;
    }

    // Returns the product.
    public String getProduct() {
        return product;
    }

    // Sets the product.
    public void setProduct(String product) {
        this.product = product;
    }
}
