package dto;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * DTO carrying the whole result screen FROM the controller OUT TO the view: every
 * classified student and the percentage of each type.
 *
 * @author HE176322
 */
public class ReportResponseDTO {

    // The classified students, in the order they were typed.
    private ArrayList<StudentResponseDTO> studentList = new ArrayList<>();
    // Type -> percent.
    private HashMap<String, Double> percentMap = new HashMap<>();

    // JavaBean constructor: an empty report, filled through the setters.
    public ReportResponseDTO() {
    }

    // Returns the students.
    public ArrayList<StudentResponseDTO> getStudentList() {
        return studentList;
    }

    // Sets the students.
    public void setStudentList(ArrayList<StudentResponseDTO> studentList) {
        this.studentList = studentList;
    }

    // Returns the statistics.
    public HashMap<String, Double> getPercentMap() {
        return percentMap;
    }

    // Sets the statistics.
    public void setPercentMap(HashMap<String, Double> percentMap) {
        this.percentMap = percentMap;
    }
}
