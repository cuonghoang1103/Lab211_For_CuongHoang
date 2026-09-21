package model;

import constants.Constants;

/**
 * MODEL: the brief's class EastAsiaCountries - a Country (inheritance) that adds its own
 * private field countryTerrain and overrides display().
 *
 * @author HE176322
 */
public class EastAsiaCountries extends Country {

    // Terrain of the country.
    private String countryTerrain;

    // JavaBean constructor: an empty country, filled through the setters.
    public EastAsiaCountries() {
        super();
    }

    // The brief's constructor with parameters: "use the super keyword to call the
    // constructor of the class Country".
    public EastAsiaCountries(String countryCode, String countryName, float totalArea,
            String countryTerrain) {
        super(countryCode, countryName, totalArea);
        this.countryTerrain = countryTerrain;
    }

    // Returns the terrain.
    public String getCountryTerrain() {
        return countryTerrain;
    }

    // Changes the terrain.
    public void setCountryTerrain(String countryTerrain) {
        this.countryTerrain = countryTerrain;
    }

    // The brief's "Override display()": the parent's three columns (reused with
    // super.display(), not copied), then the terrain - joined by a format, not by "+".
    @Override
    public String display() {
        return String.format(Constants.TERRAIN_FORMAT, super.display(), countryTerrain);
    }
}
