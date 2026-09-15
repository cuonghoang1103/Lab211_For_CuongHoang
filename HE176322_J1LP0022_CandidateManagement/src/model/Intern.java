package model;

import constants.CandidateType;
import constants.Constants;

/**
 * MODEL - SUBCLASS of Candidate: a student still studying.
 *
 * @author HE176322
 */
public class Intern extends Candidate {

    // Majors.
    private String majors;
    // Current semester; a number because semesters are counted.
    private int semester;
    // University the student is at.
    private String universityName;

    // JavaBean constructor: an empty Intern, filled through setters.
    public Intern() {
    }

    // An Intern is always type 2.
    @Override
    public CandidateType getCandidateType() {
        return CandidateType.INTERN;
    }

    // The three columns only an Intern has.
    @Override
    protected String getExtraInfo() {
        return majors + Constants.SEPARATOR + semester + Constants.SEPARATOR
                + universityName;
    }

    // Returns the majors.
    public String getMajors() {
        return majors;
    }

    // Sets the majors.
    public void setMajors(String majors) {
        this.majors = majors;
    }

    // Returns the semester.
    public int getSemester() {
        return semester;
    }

    // Sets the semester.
    public void setSemester(int semester) {
        this.semester = semester;
    }

    // Returns the university name.
    public String getUniversityName() {
        return universityName;
    }

    // Sets the university name.
    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }
}
