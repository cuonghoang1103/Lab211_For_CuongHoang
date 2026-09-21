package repository;

import constants.CandidateType;
import java.util.ArrayList;
import model.Candidate;

/**
 * INTERFACE (ISP + DIP): the two read operations the LIST and SEARCH workflows need, and
 * nothing else - no way to add a candidate through it.
 *
 * @author HE176322
 */
public interface ICandidateFinder {

    // Tells whether no candidate has been created yet.
    boolean isEmpty();

    // Returns the candidates of one kind, in the order they were created.
    ArrayList<Candidate> findByType(CandidateType type);
}
