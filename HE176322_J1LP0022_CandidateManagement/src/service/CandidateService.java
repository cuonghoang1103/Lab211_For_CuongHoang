package service;

import constants.Message;
import dto.CandidateRequestDTO;
import model.Candidate;
import repository.CandidateSaver;

/**
 * SERVICE for the CREATE workflow: the business rules that need the whole list (id not
 * empty, id not taken, names not empty), then the factory builds the candidate and the
 * repository stores it.
 *
 * @author HE176322
 */
public class CandidateService {

    // Where candidates are stored; only the two operations create needs.
    private CandidateSaver candidateSaver;
    // Builds the right subclass for the requested type.
    private CandidateFactory candidateFactory;

    // Creates the service with its dependencies (constructor injection).
    public CandidateService(CandidateSaver candidateSaver,
            CandidateFactory candidateFactory) {
        this.candidateSaver = candidateSaver;
        this.candidateFactory = candidateFactory;
    }

    // Checks the request, builds the candidate and stores it.
    public void createCandidate(CandidateRequestDTO requestDTO) throws Exception {
        // the id identifies the candidate, so it cannot be blank
        if (requestDTO.getId().isEmpty()) {
            throw new Exception(Message.ID_EMPTY);
        }
        // an id may be used by only one candidate of any kind
        if (candidateSaver.isExistId(requestDTO.getId())) {
            throw new Exception(String.format(Message.ID_EXISTS, requestDTO.getId()));
        }
        // the search works on names, so both must be given
        if (requestDTO.getFirstName().isEmpty()) {
            throw new Exception(Message.FIRST_NAME_EMPTY);
        }
        // same rule for the last name
        if (requestDTO.getLastName().isEmpty()) {
            throw new Exception(Message.LAST_NAME_EMPTY);
        }
        Candidate candidate = candidateFactory.createCandidate(requestDTO);
        candidateSaver.addCandidate(candidate);
    }
}
