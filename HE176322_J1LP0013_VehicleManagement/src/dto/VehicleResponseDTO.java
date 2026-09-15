package dto;

import constants.VehicleType;

/**
 * DTO controller -> view (and main): one row of the vehicle table.
 *
 * @author HE176322
 */
public class VehicleResponseDTO {

    // Kind of vehicle (main needs it to ask the right update questions).
    private VehicleType vehicleType;
    // Vehicle id.
    private String id;
    // Name.
    private String name;
    // Color.
    private String color;
    // Price.
    private double price;
    // Brand.
    private String brand;
    // Details column, e.g. "Type: Travel, Year: 2020".
    private String details;
    // Sound of a vehicle that can be heard, or null.
    private String sound;

    // JavaBean constructor.
    public VehicleResponseDTO() {
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
    public double getPrice() {
        return price;
    }

    // Changes the price.
    public void setPrice(double price) {
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

    // Returns the details.
    public String getDetails() {
        return details;
    }

    // Changes the details.
    public void setDetails(String details) {
        this.details = details;
    }

    // Returns the sound, or null.
    public String getSound() {
        return sound;
    }

    // Changes the sound.
    public void setSound(String sound) {
        this.sound = sound;
    }
}
