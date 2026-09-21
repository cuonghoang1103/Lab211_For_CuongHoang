package repository;

import model.Candidate;

/**
 * INTERFACE (ISP + DIP): the two storage operations the CREATE workflow needs - "is this
 * id taken?" and "store it" - and nothing else. The name starts with "I" because the
 * checklist (1.3) wants every interface named that way.
 *
 * @author HE176322
 */
public interface ICandidateSaver {

    // Tells whether a candidate already has this id (any kind, any case).
    boolean isExistId(String id);

    // Stores a candidate (already checked by the service).
    void addCandidate(Candidate candidate);
}
