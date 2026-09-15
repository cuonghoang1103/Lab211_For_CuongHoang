package model;

/**
 * BUILDER (design pattern): assembles a Student step by step.
 *
 * @author HE176322
 */
public class StudentBuilder {

    // The student being assembled; handed out by build().
    private Student student;

    // Starts a new, empty student.
    public StudentBuilder() {
        student = new Student();
    }

    // Sets the name.
    public StudentBuilder withName(String name) {
        student.setName(name);
        return this;
    }

    // Sets the class name.
    public StudentBuilder withClasses(String classes) {
        student.setClasses(classes);
        return this;
    }

    // Sets the maths mark.
    public StudentBuilder withMaths(double maths) {
        student.setMaths(maths);
        return this;
    }

    // Sets the chemistry mark.
    public StudentBuilder withChemistry(double chemistry) {
        student.setChemistry(chemistry);
        return this;
    }

    // Sets the physics mark.
    public StudentBuilder withPhysics(double physics) {
        student.setPhysics(physics);
        return this;
    }

    // Finishes the student.
    public Student build() {
        return student;
    }
}
