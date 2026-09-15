package service;

import model.Candidate;

/**
 * CONCRETE STRATEGY: the brief's search - First Name OR Last Name CONTAINS the text,
 * ignoring case.
 *
 * @author HE176322
 */
public class NameSearchStrategy implements SearchStrategy {

    // Creates the strategy; it keeps no state.
    public NameSearchStrategy() {
    }

    // Tests first name and last name separately, lower case on both sides.
    @Override
    public boolean matches(Candidate candidate, String keyword) {
        String text = keyword.toLowerCase();
        return candidate.getFirstName().toLowerCase().contains(text)
                || candidate.getLastName().toLowerCase().contains(text);
    }
}
