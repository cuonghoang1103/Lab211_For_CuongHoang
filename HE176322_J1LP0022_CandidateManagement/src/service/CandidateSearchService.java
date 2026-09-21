package service;

import constants.CandidateType;
import constants.Message;
import dto.CandidateRequestDTO;
import dto.CandidateResponseDTO;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import model.Candidate;
import repository.ICandidateFinder;

/**
 * SERVICE for the LIST and SEARCH workflows: groups candidates by kind and searches by
 * name + type (searching is business logic, so it lives in a service). No print, no
 * keyboard.
 *
 * @author HE176322
 */
public class CandidateSearchService {

    // Read-only side of the store, seen through its interface (DIP + ISP).
    private ICandidateFinder candidateFinder;

    // The matching rule of the search.
    private ISearchStrategy searchStrategy;

    // Creates the service with its dependencies (constructor injection).
    public CandidateSearchService(ICandidateFinder candidateFinder,
            ISearchStrategy searchStrategy) {
        this.candidateFinder = candidateFinder;
        this.searchStrategy = searchStrategy;
    }

    // Options 1-3, the answer N: every candidate with all its columns, grouped by kind
    // (the brief: "display all candidates who are created").
    public CandidateResponseDTO getAllCandidates() {
        CandidateResponseDTO responseDTO = new CandidateResponseDTO();

        // full lines: the Template Method toString() of each candidate
        responseDTO.setCandidateMap(groupByType(true));
        return responseDTO;
    }

    // Option 4, first flow: every name grouped by kind, shown before the questions;
    // nobody yet is thrown ("The candidate list is empty.").
    public CandidateResponseDTO getCandidateNames() throws Exception {
        CandidateResponseDTO responseDTO = new CandidateResponseDTO();

        // nobody to list or search
        if (candidateFinder.isEmpty()) {
            throw new Exception(Message.LIST_EMPTY);
        }

        // names only: the brief's search screen
        responseDTO.setCandidateMap(groupByType(false));
        return responseDTO;
    }

    // Option 4, second flow - the brief's search: the candidates of the typed type whose
    // first or last name contains the typed text, as six-column lines.
    public CandidateResponseDTO searchCandidate(CandidateRequestDTO requestDTO)
            throws Exception {
        CandidateResponseDTO responseDTO = new CandidateResponseDTO();
        ArrayList<String> foundList = new ArrayList<>();

        // a blank name would match everybody, which is not a search
        if (requestDTO.getKeyword().isEmpty()) {
            throw new Exception(Message.NAME_EMPTY);
        }

        // only the wanted kind is looked at
        for (Candidate candidate : candidateFinder.findByType(requestDTO.getType())) {
            // the strategy decides what "matches" means
            if (searchStrategy.isMatch(candidate, requestDTO.getKeyword())) {
                foundList.add(candidate.getSummary());
            }
        }

        // nobody matched: say so instead of an empty title
        if (foundList.isEmpty()) {
            throw new Exception(Message.NOT_FOUND);
        }

        responseDTO.setFoundList(foundList);
        return responseDTO;
    }

    // Every candidate grouped by kind in the brief's order (Experience, Fresher, Intern),
    // each group in creation order; a line is the full line (detailed) or the name.
    private LinkedHashMap<CandidateType, ArrayList<String>> groupByType(boolean detailed) {
        LinkedHashMap<CandidateType, ArrayList<String>> candidateMap = new LinkedHashMap<>();

        // one group per kind, even an empty one (its banner is still printed)
        for (CandidateType type : CandidateType.values()) {
            candidateMap.put(type, convertToLines(candidateFinder.findByType(type),
                    detailed));
        }

        return candidateMap;
    }

    // Copies the candidates into the lines the view may print (the view never sees the
    // model).
    private ArrayList<String> convertToLines(ArrayList<Candidate> candidateList,
            boolean detailed) {
        ArrayList<String> lineList = new ArrayList<>();

        // one line per candidate
        for (Candidate candidate : candidateList) {
            // detailed: toString() is polymorphic (Template Method), every column
            if (detailed) {
                lineList.add(candidate.toString());
            } else {
                // the search screen shows the names only
                lineList.add(candidate.getFullName());
            }
        }

        return lineList;
    }
}
