package dto;

import java.util.ArrayList;

/**
 * DTO carrying the whole report FROM the controller OUT TO the view: one row per shape, in
 * the order of the array.
 *
 * @author HE176322
 */
public class ReportResponseDTO {

    // The rows of the report, numbered from 1.
    private ArrayList<ShapeResponseDTO> rowList;

    // JavaBean constructor: an empty report, filled through the setter.
    public ReportResponseDTO() {
    }

    // Returns the rows.
    public ArrayList<ShapeResponseDTO> getRowList() {
        return rowList;
    }

    // Sets the rows.
    public void setRowList(ArrayList<ShapeResponseDTO> rowList) {
        this.rowList = rowList;
    }
}
