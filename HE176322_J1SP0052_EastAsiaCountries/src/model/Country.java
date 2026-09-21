package model;

import constants.Constants;

/**
 * MODEL: the brief's class Country - code, name and total area - with two constructors,
 * full get/set and display().
 *
 * @author HE176322
 */
public class Country {

    // Country code.
    protected String countryCode;

    // Country name.
    protected String countryName;

    // Total area in km2.
    protected float totalArea;

    // The brief's constructor "without parameter" (also the JavaBean one).
    public Country() {
    }

    // The brief's constructor "having parameter".
    public Country(String countryCode, String countryName, float totalArea) {
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.totalArea = totalArea;
    }

    // Returns the country code.
    public String getCountryCode() {
        return countryCode;
    }

    // Changes the country code.
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    // Returns the country name.
    public String getCountryName() {
        return countryName;
    }

    // Changes the country name.
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    // Returns the total area.
    public float getTotalArea() {
        return totalArea;
    }

    // Changes the total area.
    public void setTotalArea(float totalArea) {
        this.totalArea = totalArea;
    }

    // The brief's display(): the information of one country - the three columns this
    // class owns.
    public String display() {
        return String.format(Constants.COUNTRY_FORMAT, countryCode, countryName, totalArea);
    }
}
