package dto;

/**
 * DTO carrying a sorted array FROM the controller OUT TO the view: the elements in the
 * order to display, and which order that is.
 *
 * @author HE176322
 */
public class SortResponseDTO {

    // The elements, already in the order to display.
    private int[] sortedArray;

    // true: sorted ascending (option 2); false: sorted descending (option 3).
    private boolean ascending;

    // JavaBean constructor: an empty response, filled through the setters.
    public SortResponseDTO() {
    }

    // Returns the sorted elements.
    public int[] getSortedArray() {
        return sortedArray;
    }

    // Sets the sorted elements.
    public void setSortedArray(int[] sortedArray) {
        this.sortedArray = sortedArray;
    }

    // Returns true when the elements are in ascending order.
    public boolean isAscending() {
        return ascending;
    }

    // Sets the order the elements are in.
    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }
}
