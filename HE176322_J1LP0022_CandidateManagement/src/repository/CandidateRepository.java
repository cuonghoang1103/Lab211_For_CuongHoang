package repository;

import constants.CandidateType;
import java.util.ArrayList;
import model.Candidate;

/**
 * REPOSITORY: holds the ArrayList of candidates (the brief: "Create Candidate and store
 * in ArrayList") and does the simple storage operations on it. No rule, no print.
 *
 * @author HE176322
 */
public class CandidateRepository implements ICandidateRepository {

    // The "database": every candidate, in the order they were created.
    private ArrayList<Candidate> candidateList;

    // Creates an empty repository.
    public CandidateRepository() {
        candidateList = new ArrayList<>();
    }

    // Tells whether a candidate already has this id; "e01" and "E01" are the same id.
    @Override
    public boolean isExistId(String id) {
        // look at every stored candidate once
        for (Candidate candidate : candidateList) {
            // same id, whatever the capitals
            if (candidate.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }

        return false;
    }

    // Appends a candidate to the list.
    @Override
    public void addCandidate(Candidate candidate) {
        candidateList.add(candidate);
    }

    // Tells whether the list is empty.
    @Override
    public boolean isEmpty() {
        return candidateList.isEmpty();
    }

    // Returns the candidates of one kind, in creation order.
    @Override
    public ArrayList<Candidate> findByType(CandidateType type) {
        ArrayList<Candidate> typeList = new ArrayList<>();

        // keep only the candidates of the wanted kind
        for (Candidate candidate : candidateList) {
            // polymorphism: each object answers with its own class's type
            if (candidate.getCandidateType() == type) {
                typeList.add(candidate);
            }
        }

        return typeList;
    }
}
