package model;

import constants.Constants;
import constants.Message;
import constants.VehicleType;

/**
 * MODEL: a car - a Vehicle plus the type and the year of manufacture.
 *
 * @author HE176322
 */
public class Car extends Vehicle {

    // Sport, Travel, Family or Pickup.
    private String carType;

    // Year of manufacture, 1900..2100.
    private int yearOfManufacture;

    // JavaBean constructor: an empty car, filled through the setters.
    public Car() {
    }

    // A car is always of kind CAR.
    @Override
    public VehicleType getType() {
        return VehicleType.CAR;
    }

    // "Type: Travel, Year: 2020".
    @Override
    public String getDetails() {
        return String.format(Message.CAR_DETAILS, carType, yearOfManufacture);
    }

    // "Travel,2020" - the last two columns of the file.
    @Override
    protected String getDetailData() {
        return String.join(Constants.DATA_SEPARATOR, carType, String.valueOf(yearOfManufacture));
    }

    // Returns the type.
    public String getCarType() {
        return carType;
    }

    // Changes the type.
    public void setCarType(String carType) {
        this.carType = carType;
    }

    // Returns the year of manufacture.
    public int getYearOfManufacture() {
        return yearOfManufacture;
    }

    // Changes the year of manufacture.
    public void setYearOfManufacture(int yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }
}
