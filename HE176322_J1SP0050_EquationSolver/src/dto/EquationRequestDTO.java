package dto;

/**
 * DTO carrying the coefficients typed by the user, FROM main INTO the controller.
 *
 * @author HE176322
 */
public class EquationRequestDTO {

    // Coefficient A.
    private float a;
    // Coefficient B.
    private float b;
    // Coefficient C (quadratic equation only).
    private float c;

    // Creates an empty request; main fills it through the setters.
    public EquationRequestDTO() {
    }

    // Returns A.
    public float getA() {
        return a;
    }

    // Sets A.
    public void setA(float a) {
        this.a = a;
    }

    // Returns B.
    public float getB() {
        return b;
    }

    // Sets B.
    public void setB(float b) {
        this.b = b;
    }

    // Returns C.
    public float getC() {
        return c;
    }

    // Sets C.
    public void setC(float c) {
        this.c = c;
    }
}
