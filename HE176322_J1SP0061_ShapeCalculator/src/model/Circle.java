package model;

import constants.Message;

/**
 * MODEL: a circle, described by its radius.
 *
 * @author HE176322
 */
public class Circle extends Shape {

    // Radius of the circle.
    private double radius;

    // JavaBean constructor: an empty circle, filled through the setter.
    public Circle() {
    }

    // Creates a circle with its radius.
    public Circle(double radius) {
        this.radius = radius;
    }

    // Returns the radius.
    public double getRadius() {
        return radius;
    }

    // Changes the radius.
    public void setRadius(double radius) {
        this.radius = radius;
    }

    // Perimeter (circumference) of a circle: 2 * PI * r, with Math.PI as the brief asks.
    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    // Area of a circle: PI * r * r.
    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    // Template step: the title line.
    @Override
    protected String getTitle() {
        return Message.TITLE_CIRCLE;
    }

    // Template step: the radius line.
    @Override
    protected String getProperties() {
        return Message.LABEL_RADIUS + radius;
    }
}
