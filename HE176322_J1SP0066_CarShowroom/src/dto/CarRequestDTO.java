package dto;

import constants.Car;
import constants.Color;
import constants.Day;

/**
 * DTO carrying the customer's request FROM main INTO the controller.
 *
 * @author HE176322
 */
public class CarRequestDTO {

    // The car, or null when the name is not a Car.
    private Car car;
    // The colour, or null when the text is not a Color.
    private Color color;
    // The day, or null when the text is not a Day.
    private Day day;
    // The price exactly as typed.
    private String price;

    // Creates an empty request; main fills it through the setters.
    public CarRequestDTO() {
    }

    // Returns the car.
    public Car getCar() {
        return car;
    }

    // Sets the car.
    public void setCar(Car car) {
        this.car = car;
    }

    // Returns the colour.
    public Color getColor() {
        return color;
    }

    // Sets the colour.
    public void setColor(Color color) {
        this.color = color;
    }

    // Returns the day.
    public Day getDay() {
        return day;
    }

    // Sets the day.
    public void setDay(Day day) {
        this.day = day;
    }

    // Returns the price text.
    public String getPrice() {
        return price;
    }

    // Sets the price text.
    public void setPrice(String price) {
        this.price = price;
    }
}
