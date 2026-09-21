package model;

import constants.Constants;

/**
 * MODEL: one student - the five values that are typed in (name, class, three marks) and
 * the two that are worked out from them (average, type).
 *
 * @author HE176322
 */
public class Student {

    // Student name.
    private String name;

    // Class name.
    private String classes;

    // Maths mark, 0..10.
    private double maths;

    // Chemistry mark, 0..10.
    private double chemistry;

    // Physics mark, 0..10.
    private double physics;

    // Average of the three marks, one decimal; set by averageStudent.
    private double average;

    // Student type A/B/C/D; set by averageStudent.
    private String type;

    // JavaBean constructor: an empty student, filled through the setters.
    public Student() {
    }

    // Returns the name.
    public String getName() {
        return name;
    }

    // Sets the name.
    public void setName(String name) {
        this.name = name;
    }

    // Returns the class name.
    public String getClasses() {
        return classes;
    }

    // Sets the class name.
    public void setClasses(String classes) {
        this.classes = classes;
    }

    // Returns the maths mark.
    public double getMaths() {
        return maths;
    }

    // Sets the maths mark.
    public void setMaths(double maths) {
        this.maths = maths;
    }

    // Returns the chemistry mark.
    public double getChemistry() {
        return chemistry;
    }

    // Sets the chemistry mark.
    public void setChemistry(double chemistry) {
        this.chemistry = chemistry;
    }

    // Returns the physics mark.
    public double getPhysics() {
        return physics;
    }

    // Sets the physics mark.
    public void setPhysics(double physics) {
        this.physics = physics;
    }

    // Returns the average.
    public double getAverage() {
        return average;
    }

    // Sets the average.
    public void setAverage(double average) {
        this.average = average;
    }

    // Returns the type.
    public String getType() {
        return type;
    }

    // Sets the type.
    public void setType(String type) {
        this.type = type;
    }

    // Polymorphism: overrides Object.toString(); returns the text, the view prints.
    @Override
    public String toString() {
        return String.format(Constants.STUDENT_FORMAT, name, classes, average, type);
    }
}
