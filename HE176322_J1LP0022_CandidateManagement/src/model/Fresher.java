package model;

import constants.CandidateType;
import constants.Constants;
import constants.GraduationRank;

/**
 * MODEL - SUBCLASS of Candidate: a new graduate.
 *
 * @author HE176322
 */
public class Fresher extends Candidate {

    // Graduation time as typed.
    private String graduationDate;
    // Rank of graduation: one of the four legal values.
    private GraduationRank graduationRank;
    // University the student graduated from.
    private String education;

    // JavaBean constructor: an empty Fresher, filled through setters.
    public Fresher() {
    }

    // A Fresher is always type 1.
    @Override
    public CandidateType getCandidateType() {
        return CandidateType.FRESHER;
    }

    // The three columns only a Fresher has.
    @Override
    protected String getExtraInfo() {
        return graduationDate + Constants.SEPARATOR + graduationRank.getLabel()
                + Constants.SEPARATOR + education;
    }

    // Returns the graduation date.
    public String getGraduationDate() {
        return graduationDate;
    }

    // Sets the graduation date.
    public void setGraduationDate(String graduationDate) {
        this.graduationDate = graduationDate;
    }

    // Returns the rank of graduation.
    public GraduationRank getGraduationRank() {
        return graduationRank;
    }

    // Sets the rank of graduation.
    public void setGraduationRank(GraduationRank graduationRank) {
        this.graduationRank = graduationRank;
    }

    // Returns the university.
    public String getEducation() {
        return education;
    }

    // Sets the university.
    public void setEducation(String education) {
        this.education = education;
    }
}
