package service;

import constants.CandidateType;
import dto.CandidateRequestDTO;
import model.Candidate;
import model.Fresher;

/**
 * FACTORY METHOD - CONCRETE CREATOR of Fresher candidates.
 *
 * @author HE176322
 */
public class FresherCreator extends CandidateCreator {

    // Creates the creator.
    public FresherCreator() {
    }

    // This creator builds type 1.
    @Override
    public CandidateType getType() {
        return CandidateType.FRESHER;
    }

    // Builds a Fresher with its graduation date, rank and education.
    @Override
    protected Candidate newCandidate(CandidateRequestDTO requestDTO) {
        Fresher fresher = new Fresher();
        fresher.setGraduationDate(requestDTO.getGraduationDate());
        fresher.setGraduationRank(requestDTO.getGraduationRank());
        fresher.setEducation(requestDTO.getEducation());
        return fresher;
    }
}
