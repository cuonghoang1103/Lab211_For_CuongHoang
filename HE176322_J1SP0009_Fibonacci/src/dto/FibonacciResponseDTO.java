package dto;

/**
 * DTO carrying the result FROM the controller OUT TO the view: how many numbers were
 * computed and the sequence as text.
 *
 * @author HE176322
 */
public class FibonacciResponseDTO {

    // How many Fibonacci numbers the sequence holds (45).
    private int count;

    // The sequence as text.
    private String sequence;

    // JavaBean constructor: an empty response, filled through the setters.
    public FibonacciResponseDTO() {
    }

    // Returns the count.
    public int getCount() {
        return count;
    }

    // Sets the count.
    public void setCount(int count) {
        this.count = count;
    }

    // Returns the sequence.
    public String getSequence() {
        return sequence;
    }

    // Sets the sequence.
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
}
