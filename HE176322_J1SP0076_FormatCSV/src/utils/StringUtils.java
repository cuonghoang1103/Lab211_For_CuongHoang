package utils;

import constants.Constants;

/**
 * String manipulation shared by the two formatters ("Use String manipulation" - the
 * brief).
 *
 * @author HE176322
 */
public final class StringUtils {

    // Private constructor: every method is called through the class name.
    private StringUtils() {
    }

    // Removes the redundant whitespace: trims both ends and turns every run of
    // spaces/tabs into ONE space.
    public static String normalizeSpace(String text) {
        return text.trim().replaceAll(Constants.SPACES, Constants.SPACE);
    }

    // Makes the first character of every word upper case; the rest of each word is left
    // as typed (the brief only asks for the first character).
    public static String capitalizeWords(String text) {
        StringBuilder result = new StringBuilder();
        String[] wordArray = text.split(Constants.SPACE);

        // an empty field has no word to change
        if (text.isEmpty()) {
            return text;
        }

        // every word: upper-case its first letter, keep the rest
        for (int i = 0; i < wordArray.length; i++) {
            // one space between words, none before the first
            if (i > 0) {
                result.append(Constants.SPACE);
            }

            // the first letter upper case, then the rest as typed
            result.append(Character.toUpperCase(wordArray[i].charAt(0)));
            result.append(wordArray[i].substring(1));
        }

        return result.toString();
    }
}
