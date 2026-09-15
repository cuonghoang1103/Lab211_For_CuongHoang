package model;

import constants.CandidateType;
import constants.Constants;

/**
 * MODEL - SUBCLASS of Candidate: a candidate who has worked before.
 *
 * @author HE176322
 */
public class Experience extends Candidate {

    // Years of experience, 0..100.
    private int expInYear;
    // Professional skill.
    private String proSkill;

    // JavaBean constructor: an empty Experience, filled through setters.
    public Experience() {
    }

    // An Experience is always type 0.
    @Override
    public CandidateType getCandidateType() {
        return CandidateType.EXPERIENCE;
    }

    // The two columns only an Experience has.
    @Override
    protected String getExtraInfo() {
        return expInYear + Constants.SEPARATOR + proSkill;
    }

    // Returns the years of experience.
    public int getExpInYear() {
        return expInYear;
    }

    // Sets the years of experience.
    public void setExpInYear(int expInYear) {
        this.expInYear = expInYear;
    }

    // Returns the professional skill.
    public String getProSkill() {
        return proSkill;
    }

    // Sets the professional skill.
    public void setProSkill(String proSkill) {
        this.proSkill = proSkill;
    }
}
