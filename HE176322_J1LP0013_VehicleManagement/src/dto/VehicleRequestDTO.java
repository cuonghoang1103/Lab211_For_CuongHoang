package dto;

import constants.VehicleType;

/**
 * DTO main -> controller: what the user typed. Wrapper types (Double, Integer, Boolean)
 * because null means "left blank, keep the old value" in update.
 *
 * @author HE176322
 */
public class VehicleRequestDTO {

    // Kind of vehicle to create.
    private VehicleType vehicleType;
    // Vehicle id.
    private String id;
    // Name, or null to keep.
    private String name;
    // Color, or null to keep.
    private String color;
    // Price, or null to keep.
    private Double price;
    // Brand, or null to keep.
    private String brand;
    // Car type, or null to keep.
    private String carType;
    // Year of manufacture, or null to keep.
    private Integer yearOfManufacture;
    // Motorbike speed, or null to keep.
    private Double speed;
    // Motorbike license, or null to keep.
    private Boolean requireLicense;
    // Text searched in the names.
    private String keyword;

    // JavaBean constructor.
    public VehicleRequestDTO() {
    }

    // Returns the kind.
    public VehicleType getVehicleType() {
        return vehicleType;
    }

    // Changes the kind.
    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    // Returns the id.
    public String getId() {
        return id;
    }

    // Changes the id.
    public void setId(String id) {
        this.id = id;
    }

    // Returns the name.
    public String getName() {
        return name;
    }

    // Changes the name.
    public void setName(String name) {
        this.name = name;
    }

    // Returns the color.
    public String getColor() {
        return color;
    }

    // Changes the color.
    public void setColor(String color) {
        this.color = color;
    }

    // Returns the price.
    public Double getPrice() {
        return price;
    }

    // Changes the price.
    public void setPrice(Double price) {
        this.price = price;
    }

    // Returns the brand.
    public String getBrand() {
        return brand;
    }

    // Changes the brand.
    public void setBrand(String brand) {
        this.brand = brand;
    }

    // Returns the car type.
    public String getCarType() {
        return carType;
    }

    // Changes the car type.
    public void setCarType(String carType) {
        this.carType = carType;
    }

    // Returns the year of manufacture.
    public Integer getYearOfManufacture() {
        return yearOfManufacture;
    }

    // Changes the year of manufacture.
    public void setYearOfManufacture(Integer yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    // Returns the speed.
    public Double getSpeed() {
        return speed;
    }

    // Changes the speed.
    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    // Returns the license answer.
    public Boolean getRequireLicense() {
        return requireLicense;
    }

    // Changes the license answer.
    public void setRequireLicense(Boolean requireLicense) {
        this.requireLicense = requireLicense;
    }

    // Returns the search text.
    public String getKeyword() {
        return keyword;
    }

    // Changes the search text.
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
