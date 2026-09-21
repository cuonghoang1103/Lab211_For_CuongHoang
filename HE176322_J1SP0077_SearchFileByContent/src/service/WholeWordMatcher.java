package service;

import constants.Constants;

/**
 * CONCRETE STRATEGY: a match is a WHOLE word with the SAME case.
 *
 * @author HE176322
 */
public class WholeWordMatcher implements IWordMatcher {

    // Creates the matcher; it needs no data.
    public WholeWordMatcher() {
    }

    // Splits the line into words and counts the ones equal to the word.
    @Override
    public int countMatches(String line, String word) {
        int count = 0;

        // every word of the line
        for (String token : line.split(Constants.WORD_SPLIT)) {
            // exact, case-sensitive comparison
            if (token.equals(word)) {
                count++;
            }
        }

        return count;
    }
}
