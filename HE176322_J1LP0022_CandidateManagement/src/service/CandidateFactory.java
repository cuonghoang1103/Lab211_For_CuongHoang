package service;

import constants.CandidateType;
import constants.Message;
import dto.CandidateRequestDTO;
import java.util.HashMap;
import model.Candidate;

/**
 * FACTORY: picks the right concrete creator for the type in the request.
 *
 * @author HE176322
 */
public class CandidateFactory {

    // type -> the creator that builds that type.
    private HashMap<CandidateType, CandidateCreator> creatorMap = new HashMap<>();

    // Creates the factory and registers the three creators of the brief.
    public CandidateFactory() {
        register(new ExperienceCreator());
        register(new FresherCreator());
        register(new InternCreator());
    }

    // Stores a creator under the type it builds.
    private void register(CandidateCreator creator) {
        creatorMap.put(creator.getType(), creator);
    }

    // Builds the candidate the request describes.
    public Candidate createCandidate(CandidateRequestDTO requestDTO) throws Exception {
        CandidateCreator creator = creatorMap.get(requestDTO.getType());
        // a type nobody registered a creator for
        if (creator == null) {
            throw new Exception(Message.UNKNOWN_TYPE);
        }
        return creator.createCandidate(requestDTO);
    }
}
