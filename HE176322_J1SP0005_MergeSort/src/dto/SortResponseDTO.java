package dto;

/**
 * DTO carrying the result FROM the controller OUT TO the view: the array as text before
 * and after sorting.
 *
 * @author HE176322
 */
public class SortResponseDTO {

    // The array before sorting.
    private String unsorted;
    // The same array after sorting.
    private String sorted;

    // JavaBean constructor: an empty response, filled through the setters.
    public SortResponseDTO() {
    }

    // Returns the array before sorting.
    public String getUnsorted() {
        return unsorted;
    }

    // Sets the array before sorting.
    public void setUnsorted(String unsorted) {
        this.unsorted = unsorted;
    }

    // Returns the array after sorting.
    public String getSorted() {
        return sorted;
    }

    // Sets the array after sorting.
    public void setSorted(String sorted) {
        this.sorted = sorted;
    }
}
