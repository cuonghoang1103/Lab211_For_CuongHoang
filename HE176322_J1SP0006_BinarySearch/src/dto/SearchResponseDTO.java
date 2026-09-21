package dto;

/**
 * DTO carrying the result FROM the controller OUT TO the view: the sorted array as text,
 * the value searched and where it was found.
 *
 * @author HE176322
 */
public class SearchResponseDTO {

    // The array after sorting, as the screen shows it ("[1, 1, 3]").
    private String sortedArray;

    // The value the user searched for.
    private int searchValue;

    // Index of the value in the sorted array, or Constants.NOT_FOUND.
    private int index;

    // JavaBean constructor: an empty response, filled through the setters.
    public SearchResponseDTO() {
    }

    // Returns the sorted array.
    public String getSortedArray() {
        return sortedArray;
    }

    // Sets the sorted array.
    public void setSortedArray(String sortedArray) {
        this.sortedArray = sortedArray;
    }

    // Returns the value searched for.
    public int getSearchValue() {
        return searchValue;
    }

    // Sets the value searched for.
    public void setSearchValue(int searchValue) {
        this.searchValue = searchValue;
    }

    // Returns where the value was found.
    public int getIndex() {
        return index;
    }

    // Sets where the value was found.
    public void setIndex(int index) {
        this.index = index;
    }
}
