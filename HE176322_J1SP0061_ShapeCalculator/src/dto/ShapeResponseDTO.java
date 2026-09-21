package dto;

import java.util.ArrayList;

/**
 * DTO carrying the results FROM the controller OUT TO the view - a JavaBean. One text block
 * per shape: title, properties, area, perimeter.
 *
 * @author HE176322
 */
public class ShapeResponseDTO {

    // The result blocks, in the order of the brief's screen: rectangle, circle, triangle.
    private ArrayList<String> resultList;

    // JavaBean constructor: an empty response, filled through the setter.
    public ShapeResponseDTO() {
    }

    // Returns the result blocks.
    public ArrayList<String> getResultList() {
        return resultList;
    }

    // Sets the result blocks.
    public void setResultList(ArrayList<String> resultList) {
        this.resultList = resultList;
    }
}
