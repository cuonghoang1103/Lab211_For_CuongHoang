package service;

import model.Candidate;

/**
 * STRATEGY (design pattern): the rule deciding whether a candidate matches the search
 * text. An interface, so its name starts with "I" (checklist 1.3).
 *
 * @author HE176322
 */
public interface ISearchStrategy {

    // Tells whether the candidate matches the keyword.
    boolean isMatch(Candidate candidate, String keyword);
}
