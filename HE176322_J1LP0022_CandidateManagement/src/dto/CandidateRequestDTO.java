package dto;

import constants.CandidateType;
import constants.GraduationRank;

/**
 * DTO carrying what the user typed FROM main INTO the controller - a JavaBean.
 *
 * @author HE176322
 */
public class CandidateRequestDTO {

    // Kind of candidate chosen from the menu (or typed in search).
    private CandidateType type;

    // Candidate id.
    private String id;

    // First name.
    private String firstName;

    // Last name.
    private String lastName;

    // Birth year, already validated.
    private int birthDate;

    // Address.
    private String address;

    // Phone, already validated.
    private String phone;

    // Email, already validated.
    private String email;

    // Experience only: years of experience.
    private int expInYear;

    // Experience only: professional skill.
    private String proSkill;

    // Fresher only: graduation date.
    private String graduationDate;

    // Fresher only: rank of graduation.
    private GraduationRank graduationRank;

    // Fresher only: university graduated from.
    private String education;

    // Intern only: majors.
    private String majors;

    // Intern only: semester.
    private int semester;

    // Intern only: university name.
    private String universityName;

    // Search only: first or last name to look for.
    private String keyword;

    // Creates an empty request; main fills it through the setters.
    public CandidateRequestDTO() {
    }

    // Returns the kind of candidate chosen from the menu (or typed in search).
    public CandidateType getType() {
        return type;
    }

    // Sets the kind of candidate chosen from the menu (or typed in search).
    public void setType(CandidateType type) {
        this.type = type;
    }

    // Returns the candidate id.
    public String getId() {
        return id;
    }

    // Sets the candidate id.
    public void setId(String id) {
        this.id = id;
    }

    // Returns the first name.
    public String getFirstName() {
        return firstName;
    }

    // Sets the first name.
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Returns the last name.
    public String getLastName() {
        return lastName;
    }

    // Sets the last name.
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Returns the birth year, already validated.
    public int getBirthDate() {
        return birthDate;
    }

    // Sets the birth year, already validated.
    public void setBirthDate(int birthDate) {
        this.birthDate = birthDate;
    }

    // Returns the address.
    public String getAddress() {
        return address;
    }

    // Sets the address.
    public void setAddress(String address) {
        this.address = address;
    }

    // Returns the phone, already validated.
    public String getPhone() {
        return phone;
    }

    // Sets the phone, already validated.
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Returns the email, already validated.
    public String getEmail() {
        return email;
    }

    // Sets the email, already validated.
    public void setEmail(String email) {
        this.email = email;
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

    // Returns the university graduated from.
    public String getEducation() {
        return education;
    }

    // Sets the university graduated from.
    public void setEducation(String education) {
        this.education = education;
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

    // Returns the first or last name to look for.
    public String getKeyword() {
        return keyword;
    }

    // Sets the first or last name to look for.
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
