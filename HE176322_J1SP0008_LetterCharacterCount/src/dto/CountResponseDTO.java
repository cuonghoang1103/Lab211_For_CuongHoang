package dto;

import java.util.ArrayList;

/**
 * DTO carrying the result FROM the controller OUT TO the view: one line of text per count.
 *
 * @author HE176322
 */
public class CountResponseDTO {

    // The result lines, in the order they are shown.
    private ArrayList<String> resultList;

    // JavaBean constructor: an empty response.
    public CountResponseDTO() {
        resultList = new ArrayList<>();
    }

    // Returns the result lines.
    public ArrayList<String> getResultList() {
        return resultList;
    }

    // Replaces the result lines.
    public void setResultList(ArrayList<String> resultList) {
        this.resultList = resultList;
    }

    // Appends one result line.
    public void addResult(String result) {
        resultList.add(result);
    }
}
