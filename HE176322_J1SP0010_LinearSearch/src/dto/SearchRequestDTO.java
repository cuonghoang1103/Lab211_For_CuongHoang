package dto;

/**
 * DTO carrying what the user typed FROM main INTO the controller: the size of the array
 * to generate and the value to look for.
 *
 * @author HE176322
 */
public class SearchRequestDTO {

    // How many random numbers to generate.
    private int size;
    // The number to look for in the array.
    private int searchValue;

    // Creates an empty request; main fills it through the setters.
    public SearchRequestDTO() {
    }

    // Returns the size.
    public int getSize() {
        return size;
    }

    // Sets the size.
    public void setSize(int size) {
        this.size = size;
    }

    // Returns the value to search.
    public int getSearchValue() {
        return searchValue;
    }

    // Sets the value to search.
    public void setSearchValue(int searchValue) {
        this.searchValue = searchValue;
    }
}
