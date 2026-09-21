package model;

import constants.Constants;
import constants.Course;

/**
 * MODEL: one student, with exactly the four attributes the Guidelines name - id,
 * studentName, semester and courseName.
 *
 * @author HE176322
 */
public class Student {

    // Unique id.
    private String id;

    // Full name.
    private String studentName;

    // Semester number, greater than 0.
    private int semester;

    // One of the three courses.
    private Course courseName;

    // JavaBean constructor: an empty student, filled through the setters.
    public Student() {
    }

    // Creates a student with every field filled in.
    public Student(String id, String studentName, int semester, Course courseName) {
        this.id = id;
        this.studentName = studentName;
        this.semester = semester;
        this.courseName = courseName;
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
    public String getStudentName() {
        return studentName;
    }

    // Changes the name.
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    // Returns the semester.
    public int getSemester() {
        return semester;
    }

    // Changes the semester.
    public void setSemester(int semester) {
        this.semester = semester;
    }

    // Returns the course.
    public Course getCourseName() {
        return courseName;
    }

    // Changes the course.
    public void setCourseName(Course courseName) {
        this.courseName = courseName;
    }

    // Tells whether this student's name contains the text (already in lower case),
    // ignoring case - the brief's "a part of student name".
    public boolean isNameContains(String text) {
        return studentName.toLowerCase().contains(text);
    }

    // Polymorphism: overrides Object.toString() with the row of the Find and Sort table -
    // name, semester, course; the model returns the text, the view prints it.
    @Override
    public String toString() {
        return String.format(Constants.SEARCH_ROW_FORMAT, studentName, semester, courseName);
    }
}
