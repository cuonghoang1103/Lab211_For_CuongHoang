package dto;

/**
 * DTO carrying the coefficients typed by the user, FROM main INTO the controller.
 *
 * @author HE176322
 */
public class EquationRequestDTO {

    // Coefficient A.
    private float coefficientA;

    // Coefficient B.
    private float coefficientB;

    // Coefficient C (quadratic equation only).
    private float coefficientC;

    // Creates an empty request; main fills it through the setters.
    public EquationRequestDTO() {
    }

    // Returns A.
    public float getCoefficientA() {
        return coefficientA;
    }

    // Sets A.
    public void setCoefficientA(float coefficientA) {
        this.coefficientA = coefficientA;
    }

    // Returns B.
    public float getCoefficientB() {
        return coefficientB;
    }

    // Sets B.
    public void setCoefficientB(float coefficientB) {
        this.coefficientB = coefficientB;
    }

    // Returns C.
    public float getCoefficientC() {
        return coefficientC;
    }

    // Sets C.
    public void setCoefficientC(float coefficientC) {
        this.coefficientC = coefficientC;
    }
}
