package dto;

import java.util.ArrayList;

/**
 * DTO carrying the result FROM the controller OUT TO the view: one line of text per count.
 *
 * @author HE176322
 */
public class CountResponseDTO {

    // The result lines, in the order they are shown.
    private ArrayList<String> results;

    // JavaBean constructor: an empty response.
    public CountResponseDTO() {
        this.results = new ArrayList<>();
    }

    // Returns the result lines.
    public ArrayList<String> getResults() {
        return results;
    }

    // Replaces the result lines.
    public void setResults(ArrayList<String> results) {
        this.results = results;
    }

    // Appends one result line.
    public void addResult(String result) {
        results.add(result);
    }
}
