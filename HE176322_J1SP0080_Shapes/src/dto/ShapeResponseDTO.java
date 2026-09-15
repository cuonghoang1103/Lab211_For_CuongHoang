package dto;

/**
 * DTO carrying ONE row of the report FROM the controller OUT TO the view.
 *
 * @author HE176322
 */
public class ShapeResponseDTO {

    // Row number, from 1.
    private int no;
    // The shape's toString().
    private String description;
    // Area (surface area for a solid).
    private double area;
    // Volume; meaningful only when threeDimensional is true.
    private double volume;
    // True for a ThreeDimensionalShape: the view then prints the volume.
    private boolean threeDimensional;

    // JavaBean constructor: an empty row, filled through the setters.
    public ShapeResponseDTO() {
    }

    // Returns the row number.
    public int getNo() {
        return no;
    }

    // Sets the row number.
    public void setNo(int no) {
        this.no = no;
    }

    // Returns the description.
    public String getDescription() {
        return description;
    }

    // Sets the description.
    public void setDescription(String description) {
        this.description = description;
    }

    // Returns the area.
    public double getArea() {
        return area;
    }

    // Sets the area.
    public void setArea(double area) {
        this.area = area;
    }

    // Returns the volume.
    public double getVolume() {
        return volume;
    }

    // Sets the volume.
    public void setVolume(double volume) {
        this.volume = volume;
    }

    // Tells whether the shape is three-dimensional ("is" getter: JavaBean naming for a
    // boolean).
    public boolean isThreeDimensional() {
        return threeDimensional;
    }

    // Sets whether the shape is three-dimensional.
    public void setThreeDimensional(boolean threeDimensional) {
        this.threeDimensional = threeDimensional;
    }
}
