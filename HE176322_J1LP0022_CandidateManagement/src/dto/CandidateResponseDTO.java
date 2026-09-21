package dto;

import constants.CandidateType;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * DTO carrying the answer of ONE flow FROM the controller OUT TO the view - a JavaBean,
 * handed to the view through its attribute (checklist 1.1). Each part is null when the
 * flow has nothing of it; the view prints only the parts that are set.
 *
 * @author HE176322
 */
public class CandidateResponseDTO {

    // The one-line result, e.g. "Experience candidate [E01] has been created.", or null.
    private String message;

    // The listing under "List of candidate:": the lines of each kind, the kinds in the
    // brief's order - full lines after creating, names only on the search screen.
    private LinkedHashMap<CandidateType, ArrayList<String>> candidateMap;

    // The search result under "The candidates found:": one six-column line per match.
    private ArrayList<String> foundList;

    // JavaBean constructor: an empty answer, filled through the setters.
    public CandidateResponseDTO() {
    }

    // Returns the one-line result.
    public String getMessage() {
        return message;
    }

    // Sets the one-line result.
    public void setMessage(String message) {
        this.message = message;
    }

    // Returns the grouped lines.
    public LinkedHashMap<CandidateType, ArrayList<String>> getCandidateMap() {
        return candidateMap;
    }

    // Sets the grouped lines.
    public void setCandidateMap(LinkedHashMap<CandidateType, ArrayList<String>> candidateMap) {
        this.candidateMap = candidateMap;
    }

    // Returns the lines of the search result.
    public ArrayList<String> getFoundList() {
        return foundList;
    }

    // Sets the lines of the search result.
    public void setFoundList(ArrayList<String> foundList) {
        this.foundList = foundList;
    }
}
