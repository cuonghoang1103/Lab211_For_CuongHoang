package service;

import constants.Message;
import dto.CandidateRequestDTO;
import dto.CandidateResponseDTO;
import repository.ICandidateSaver;

/**
 * SERVICE for the CREATE workflow: the business rules that need the whole list (id not
 * empty, id not taken, names not empty), then the factory builds the candidate and the
 * repository stores it. No print, no keyboard.
 *
 * @author HE176322
 */
public class CandidateService {

    // Where candidates are stored - seen through the interface of the two operations
    // create needs (DIP + ISP).
    private ICandidateSaver candidateSaver;

    // Builds the right subclass for the requested type.
    private CandidateFactory candidateFactory;

    // Creates the service with its dependencies (constructor injection).
    public CandidateService(ICandidateSaver candidateSaver,
            CandidateFactory candidateFactory) {
        this.candidateSaver = candidateSaver;
        this.candidateFactory = candidateFactory;
    }

    // Options 1-3, one candidate: checks the request, builds the candidate, stores it and
    // answers with "Experience candidate [E01] has been created.".
    public CandidateResponseDTO createCandidate(CandidateRequestDTO requestDTO)
            throws Exception {
        CandidateResponseDTO responseDTO = new CandidateResponseDTO();

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

        // the factory picks the subclass, the repository keeps it
        candidateSaver.addCandidate(candidateFactory.createCandidate(requestDTO));
        responseDTO.setMessage(String.format(Message.CREATE_SUCCESS,
                requestDTO.getType().getLabel(), requestDTO.getId()));
        return responseDTO;
    }
}
