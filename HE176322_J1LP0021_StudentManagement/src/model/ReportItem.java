package model;

import constants.Course;

/**
 * MODEL: one line of the report - a student name, a course, and how many times that pair
 * appears in the list (the brief: "Nguyen Van A | Java | 2").
 *
 * @author HE176322
 */
public class ReportItem {

    // Student name as it was first met in the list.
    private String studentName;
    // The course of this line.
    private Course course;
    // How many times this name took this course.
    private int total;

    // JavaBean constructor: an empty line, filled through the setters.
    public ReportItem() {
    }

    // Creates the line for the first student met with this name and course, so the total
    // starts at 1.
    public ReportItem(String studentName, Course course) {
        this.studentName = studentName;
        this.course = course;
        this.total = 1;
    }

    // Returns the name.
    public String getStudentName() {
        return studentName;
    }

    // Changes the name.
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    // Returns the course.
    public Course getCourse() {
        return course;
    }

    // Changes the course.
    public void setCourse(Course course) {
        this.course = course;
    }

    // Returns the total.
    public int getTotal() {
        return total;
    }

    // Changes the total.
    public void setTotal(int total) {
        this.total = total;
    }

    // Counts one more student with the same name and course.
    public void increaseTotal() {
        total++;
    }

    // Polymorphism: overrides Object.toString().
    @Override
    public String toString() {
        return studentName + " " + course + " " + total;
    }
}
