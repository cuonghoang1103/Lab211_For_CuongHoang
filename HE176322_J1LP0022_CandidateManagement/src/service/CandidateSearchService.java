package service;

import constants.CandidateType;
import constants.Message;
import dto.CandidateRequestDTO;
import dto.CandidateResponseDTO;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import model.Candidate;
import repository.CandidateFinder;

/**
 * SERVICE for the LIST and SEARCH workflows: groups candidates by kind and searches by
 * name + type (searching is business logic, so it lives in a service).
 *
 * @author HE176322
 */
public class CandidateSearchService {

    // Read-only view of the storage.
    private CandidateFinder candidateFinder;
    // The matching rule of the search.
    private SearchStrategy searchStrategy;

    // Creates the service with its dependencies (constructor injection).
    public CandidateSearchService(CandidateFinder candidateFinder,
            SearchStrategy searchStrategy) {
        this.candidateFinder = candidateFinder;
        this.searchStrategy = searchStrategy;
    }

    // Stops the search workflow before any question when nothing exists yet.
    public void checkNotEmpty() throws Exception {
        // nobody to list or search
        if (candidateFinder.isEmpty()) {
            throw new Exception(Message.LIST_EMPTY);
        }
    }

    // Every candidate, grouped by kind in the brief's order (Experience, Fresher,
    // Intern); each group keeps creation order.
    public LinkedHashMap<CandidateType, ArrayList<CandidateResponseDTO>> getCandidatesByType() {
        LinkedHashMap<CandidateType, ArrayList<CandidateResponseDTO>> groups
                = new LinkedHashMap<>();
        // one group per kind, even an empty one (its banner is still printed)
        for (CandidateType type : CandidateType.values()) {
            groups.put(type, toResponseList(candidateFinder.findByType(type)));
        }
        return groups;
    }

    // The brief's search: candidates of the typed type whose first or last name contains
    // the typed text.
    public ArrayList<CandidateResponseDTO> searchCandidate(CandidateRequestDTO requestDTO)
            throws Exception {
        checkNotEmpty();
        // a blank name would match everybody, which is not a search
        if (requestDTO.getKeyword().isEmpty()) {
            throw new Exception(Message.NAME_EMPTY);
        }
        ArrayList<Candidate> found = new ArrayList<>();
        // only the wanted kind is looked at
        for (Candidate candidate : candidateFinder.findByType(requestDTO.getType())) {
            // the strategy decides what "matches" means
            if (searchStrategy.matches(candidate, requestDTO.getKeyword())) {
                found.add(candidate);
            }
        }
        return toResponseList(found);
    }

    // Copies model objects into the rows the view may see.
    private ArrayList<CandidateResponseDTO> toResponseList(ArrayList<Candidate> candidates) {
        ArrayList<CandidateResponseDTO> rows = new ArrayList<>();
        // convert each candidate; toString() is polymorphic (Template Method)
        for (Candidate candidate : candidates) {
            CandidateResponseDTO row = new CandidateResponseDTO();
            row.setFullName(candidate.getFullName());
            row.setSummary(candidate.getSummary());
            row.setDetail(candidate.toString());
            rows.add(row);
        }
        return rows;
    }
}
