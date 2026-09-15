package service;

import constants.CandidateType;
import dto.CandidateRequestDTO;
import model.Candidate;

/**
 * FACTORY METHOD - the abstract CREATOR.
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
    protected abstract Candidate newCandidate(CandidateRequestDTO requestDTO);

    // Builds a complete candidate from the request.
    public final Candidate createCandidate(CandidateRequestDTO requestDTO) {
        Candidate candidate = newCandidate(requestDTO);
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
