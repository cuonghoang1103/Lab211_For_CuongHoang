package model;

import constants.Car;
import constants.Color;
import constants.Constants;
import constants.Day;

/**
 * MODEL: one customer's request once it has been read correctly - which car, which
 * colour, which day, and how much the customer offers.
 *
 * @author HE176322
 */
public class CarOrder {

    // The car asked for.
    private Car car;

    // The colour asked for; NO_COLOR for an unpainted car.
    private Color color;

    // The day of the request.
    private Day day;

    // The price the customer offers; double because it may have decimals.
    private double price;

    // JavaBean constructor: an empty order, filled through the setters.
    public CarOrder() {
    }

    // Creates an order with every field filled in.
    public CarOrder(Car car, Color color, Day day, double price) {
        this.car = car;
        this.color = color;
        this.day = day;
        this.price = price;
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

    // Returns the price offered.
    public double getPrice() {
        return price;
    }

    // Sets the price offered.
    public void setPrice(double price) {
        this.price = price;
    }

    // Polymorphism: overrides Object.toString() so the debugger shows the order on one
    // line (String.format, no string "+").
    @Override
    public String toString() {
        return String.format(Constants.ORDER_FORMAT, car, color, day, price);
    }
}
