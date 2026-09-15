package view;

import constants.CandidateType;
import constants.Message;
import dto.CandidateResponseDTO;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * VIEW: the only place (with main) that prints results.
 *
 * @author HE176322
 */
public class CandidateView {

    // Every candidate grouped by kind, for the two listing screens.
    private LinkedHashMap<CandidateType, ArrayList<CandidateResponseDTO>> candidateMap;
    // The search result.
    private ArrayList<CandidateResponseDTO> foundList;

    // Receives the grouped candidates.
    public void setCandidateMap(
            LinkedHashMap<CandidateType, ArrayList<CandidateResponseDTO>> candidateMap) {
        this.candidateMap = candidateMap;
    }

    // Receives the search result.
    public void setFoundList(ArrayList<CandidateResponseDTO> foundList) {
        this.foundList = foundList;
    }

    // Listing after creating: every column of every candidate.
    public void displayDetails() {
        displayGroups(true);
    }

    // Listing of the search screen: names only (the brief's sample).
    public void displayNames() {
        displayGroups(false);
    }

    // Prints "List of candidate:" then each banner with its candidates.
    private void displayGroups(boolean detailed) {
        System.out.println(Message.LIST_TITLE);
        // one group per kind, in the order the service put them in
        for (CandidateType type : candidateMap.keySet()) {
            System.out.println(type.getBanner());
            // one line per candidate of this kind
            for (CandidateResponseDTO row : candidateMap.get(type)) {
                // after creating: all columns; in search: the name only
                if (detailed) {
                    System.out.println(row.getDetail());
                } else {
                    // search screen shows names only
                    System.out.println(row.getFullName());
                }
            }
        }
    }

    // Prints the search result, or "No candidate found.".
    public void displayFound() {
        // nobody matched: say so, silence would look like a crash
        if (foundList.isEmpty()) {
            System.out.println(Message.NOT_FOUND);
            return;
        }
        System.out.println(Message.FOUND_TITLE);
        // one six-column line per match
        for (CandidateResponseDTO row : foundList) {
            System.out.println(row.getSummary());
        }
    }

    // Prints a one-line result.
    public void showMessage(String message) {
        System.out.println(message);
    }
}
