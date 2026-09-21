package model;

import constants.Constants;
import constants.VehicleType;

/**
 * MODEL: what every vehicle of the show room has. Abstract: each vehicle is a Car or a
 * Motorbike, so ONE ArrayList of Vehicle holds both.
 *
 * @author HE176322
 */
public abstract class Vehicle {

    // Unique id, e.g. C001.
    private String id;

    // Name, e.g. Camry.
    private String name;

    // Color.
    private String color;

    // Price, greater than 0.
    private double price;

    // Brand, e.g. Toyota.
    private String brand;

    // JavaBean constructor; only subclasses call it.
    protected Vehicle() {
    }

    // Which kind this vehicle is (each subclass answers for itself).
    public abstract VehicleType getType();

    // The properties only this kind has, as the Details column shows them.
    public abstract String getDetails();

    // Template Method step: the columns only this kind saves in the file.
    protected abstract String getDetailData();

    // TEMPLATE METHOD: the line saved in vehicles.txt = kind and common columns, then the
    // subclass's own columns (String.join puts the separator between them, no "+").
    public final String toDataLine() {
        return String.join(Constants.DATA_SEPARATOR, getType().getCode(), id, name, color,
                String.valueOf(price), brand, getDetailData());
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
}
