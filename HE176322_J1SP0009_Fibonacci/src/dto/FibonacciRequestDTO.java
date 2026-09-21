package dto;

/**
 * DTO carrying what main hands INTO the controller: how many Fibonacci numbers to compute.
 * The brief fixes it at 45, so main fills it from Constants instead of the keyboard.
 *
 * @author HE176322
 */
public class FibonacciRequestDTO {

    // How many Fibonacci numbers to compute (45).
    private int count;

    // Creates an empty request; main fills it through the setter.
    public FibonacciRequestDTO() {
    }

    // Returns the count.
    public int getCount() {
        return count;
    }

    // Sets the count.
    public void setCount(int count) {
        this.count = count;
    }
}
