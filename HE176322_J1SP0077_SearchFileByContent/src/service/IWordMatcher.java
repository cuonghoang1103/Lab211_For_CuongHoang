package service;

/**
 * STRATEGY (design pattern): what counts as "an occurrence of the word". The name starts
 * with I (checklist 1.3: an interface name begins with "I").
 *
 * @author HE176322
 */
public interface IWordMatcher {

    // Counts the occurrences of the word in one line.
    int countMatches(String line, String word);
}
