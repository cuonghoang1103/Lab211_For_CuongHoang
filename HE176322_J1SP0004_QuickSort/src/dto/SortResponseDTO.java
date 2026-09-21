package dto;

/**
 * DTO carrying the result FROM the controller OUT TO the view: the array as text before
 * and after sorting.
 *
 * @author HE176322
 */
public class SortResponseDTO {

    // The array before sorting, as the screen shows it ("[2, 6, 3]").
    private String unsortedArray;

    // The same array after sorting, as the screen shows it.
    private String sortedArray;

    // JavaBean constructor: an empty response, filled through the setters.
    public SortResponseDTO() {
    }

    // Returns the array before sorting.
    public String getUnsortedArray() {
        return unsortedArray;
    }

    // Sets the array before sorting.
    public void setUnsortedArray(String unsortedArray) {
        this.unsortedArray = unsortedArray;
    }

    // Returns the array after sorting.
    public String getSortedArray() {
        return sortedArray;
    }

    // Sets the array after sorting.
    public void setSortedArray(String sortedArray) {
        this.sortedArray = sortedArray;
    }
}
