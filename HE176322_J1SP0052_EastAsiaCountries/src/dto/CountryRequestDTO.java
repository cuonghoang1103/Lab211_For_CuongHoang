package dto;

/**
 * DTO carrying what the user typed FROM main INTO the controller: a new country (option
 * 1) or a name to search (option 3).
 *
 * @author HE176322
 */
public class CountryRequestDTO {

    // Code typed on input.
    private String countryCode;
    // Name typed on input.
    private String countryName;
    // Total area typed on input, already checked to be greater than 0.
    private float totalArea;
    // Terrain typed on input.
    private String countryTerrain;
    // Name typed on search.
    private String searchName;

    // Creates an empty request; main fills it through the setters.
    public CountryRequestDTO() {
    }

    // Returns the code.
    public String getCountryCode() {
        return countryCode;
    }

    // Sets the code.
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    // Returns the name.
    public String getCountryName() {
        return countryName;
    }

    // Sets the name.
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    // Returns the total area.
    public float getTotalArea() {
        return totalArea;
    }

    // Sets the total area.
    public void setTotalArea(float totalArea) {
        this.totalArea = totalArea;
    }

    // Returns the terrain.
    public String getCountryTerrain() {
        return countryTerrain;
    }

    // Sets the terrain.
    public void setCountryTerrain(String countryTerrain) {
        this.countryTerrain = countryTerrain;
    }

    // Returns the name to search.
    public String getSearchName() {
        return searchName;
    }

    // Sets the name to search.
    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }
}
