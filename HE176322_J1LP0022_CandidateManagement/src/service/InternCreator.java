package service;

import constants.CandidateType;
import dto.CandidateRequestDTO;
import model.Candidate;
import model.Intern;

/**
 * FACTORY METHOD - CONCRETE CREATOR of Intern candidates.
 *
 * @author HE176322
 */
public class InternCreator extends CandidateCreator {

    // Creates the creator.
    public InternCreator() {
    }

    // This creator builds type 2.
    @Override
    public CandidateType getType() {
        return CandidateType.INTERN;
    }

    // Builds an Intern with its majors, semester and university.
    @Override
    protected Candidate buildCandidate(CandidateRequestDTO requestDTO) {
        Intern intern = new Intern();

        // the three fields only an Intern has
        intern.setMajors(requestDTO.getMajors());
        intern.setSemester(requestDTO.getSemester());
        intern.setUniversityName(requestDTO.getUniversityName());
        return intern;
    }
}
