package service;

import constants.CandidateType;
import dto.CandidateRequestDTO;
import model.Candidate;

/**
 * FACTORY METHOD - the abstract CREATOR: each subclass builds ONE kind of candidate
 * (buildCandidate), and createCandidate fills the seven common fields once for all kinds.
 *
 * @author HE176322
 */
public abstract class CandidateCreator {

    // Creates the creator; it keeps no state.
    protected CandidateCreator() {
    }

    // The kind of candidate this creator builds; the factory uses it as the key of its
    // map.
    public abstract CandidateType getType();

    // FACTORY METHOD: builds the concrete candidate and fills the fields only that kind
    // has.
    protected abstract Candidate buildCandidate(CandidateRequestDTO requestDTO);

    // TEMPLATE METHOD: builds a complete candidate from the request - the subclass builds
    // its own kind, then the seven common fields are filled here.
    public final Candidate createCandidate(CandidateRequestDTO requestDTO) {
        Candidate candidate = buildCandidate(requestDTO);

        // the seven fields every kind has
        candidate.setId(requestDTO.getId());
        candidate.setFirstName(requestDTO.getFirstName());
        candidate.setLastName(requestDTO.getLastName());
        candidate.setBirthDate(requestDTO.getBirthDate());
        candidate.setAddress(requestDTO.getAddress());
        candidate.setPhone(requestDTO.getPhone());
        candidate.setEmail(requestDTO.getEmail());
        return candidate;
    }
}
