package service;

import constants.CandidateType;
import dto.CandidateRequestDTO;
import model.Candidate;
import model.Experience;

/**
 * FACTORY METHOD - CONCRETE CREATOR of Experience candidates.
 *
 * @author HE176322
 */
public class ExperienceCreator extends CandidateCreator {

    // Creates the creator.
    public ExperienceCreator() {
    }

    // This creator builds type 0.
    @Override
    public CandidateType getType() {
        return CandidateType.EXPERIENCE;
    }

    // Builds an Experience with its years and skill.
    @Override
    protected Candidate buildCandidate(CandidateRequestDTO requestDTO) {
        Experience experience = new Experience();

        // the two fields only an Experience has
        experience.setExpInYear(requestDTO.getExpInYear());
        experience.setProSkill(requestDTO.getProSkill());
        return experience;
    }
}
