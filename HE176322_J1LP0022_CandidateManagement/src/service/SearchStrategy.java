package service;

import model.Candidate;

/**
 * STRATEGY (design pattern): the rule deciding whether a candidate matches the search
 * text.
 *
 * @author HE176322
 */
public interface SearchStrategy {

    // Tells whether the candidate matches the keyword.
    boolean matches(Candidate candidate, String keyword);
}
