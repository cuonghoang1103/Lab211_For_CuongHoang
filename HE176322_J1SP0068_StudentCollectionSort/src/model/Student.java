package model;

import constants.Constants;

/**
 * MODEL: one student - exactly the class the brief's Hint describes: private name, mark,
 * classes; a default constructor; a constructor with parameters; getters/setters.
 *
 * @author HE176322
 */
public class Student {

    // Full name; the sort key (A to Z).
    private String name;

    // Mark from 0 to 100; float because the brief declares "float mark".
    private float mark;

    // Class of the student.
    private String classes;

    // Default constructor (the brief's Hint).
    public Student() {
    }

    // Constructor with parameters (the brief's Hint).
    public Student(String name, String classes, float mark) {
        this.name = name;
        this.classes = classes;
        this.mark = mark;
    }

    // Returns the name.
    public String getName() {
        return name;
    }

    // Changes the name.
    public void setName(String name) {
        this.name = name;
    }

    // Returns the mark.
    public float getMark() {
        return mark;
    }

    // Changes the mark.
    public void setMark(float mark) {
        this.mark = mark;
    }

    // Returns the class.
    public String getClasses() {
        return classes;
    }

    // Changes the class.
    public void setClasses(String classes) {
        this.classes = classes;
    }

    // Polymorphism: overrides Object.toString() so a student reads as one line.
    @Override
    public String toString() {
        return String.format(Constants.STUDENT_FORMAT, name, classes, mark);
    }
}
