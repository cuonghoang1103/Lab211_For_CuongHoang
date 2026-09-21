package view;

import constants.CandidateType;
import constants.Message;
import dto.CandidateResponseDTO;

/**
 * VIEW: the only place (with main) that prints results. It receives the data through its
 * attribute (the ResponseDTO, as in the Guide sample), never through the parameters of
 * display().
 *
 * @author HE176322
 */
public class CandidateView {

    // The answer to print, handed over by the controller.
    private CandidateResponseDTO responseDTO;

    // Receives the answer the next display() call will print.
    public void setResponseDTO(CandidateResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints what the controller set, in this order: the result line, the grouped
    // listing, the search result.
    public void display() {
        // a one-line result such as "Experience candidate [E01] has been created."
        if (responseDTO.getMessage() != null) {
            System.out.println(responseDTO.getMessage());
        }

        // the listing: after creating, and first on the search screen
        if (responseDTO.getCandidateMap() != null) {
            displayGroups();
        }

        // the result of the search
        if (responseDTO.getFoundList() != null) {
            displayFound();
        }
    }

    // Prints "List of candidate:", then each banner of the brief with its lines.
    private void displayGroups() {
        System.out.println(Message.LIST_TITLE);

        // one group per kind, in the order the service put them in
        for (CandidateType type : responseDTO.getCandidateMap().keySet()) {
            System.out.println(type.getBanner());

            // one line per candidate of this kind
            for (String line : responseDTO.getCandidateMap().get(type)) {
                System.out.println(line);
            }
        }
    }

    // Prints the brief's result: an empty line, "The candidates found:", then one
    // six-column line per match.
    private void displayFound() {
        System.out.println(Message.FOUND_TITLE);

        // one line per match
        for (String line : responseDTO.getFoundList()) {
            System.out.println(line);
        }
    }
}
