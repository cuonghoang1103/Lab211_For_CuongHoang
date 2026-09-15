package service;

/**
 * STRATEGY (design pattern): what counts as "an occurrence of the word".
 *
 * @author HE176322
 */
public interface WordMatcher {

    // Counts the occurrences of the word in one line.
    int countMatches(String line, String word);
}
