package dto;

/**
 * DTO carrying the result FROM the controller OUT TO the view: the array as text, the
 * value searched and where it was found.
 *
 * @author HE176322
 */
public class SearchResponseDTO {

    // The array as generated.
    private String array;
    // The value the user searched for.
    private int searchValue;
    // Index of the first match, or Constants.NOT_FOUND.
    private int index;

    // JavaBean constructor: an empty response, filled through the setters.
    public SearchResponseDTO() {
    }

    // Returns the array.
    public String getArray() {
        return array;
    }

    // Sets the array.
    public void setArray(String array) {
        this.array = array;
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
