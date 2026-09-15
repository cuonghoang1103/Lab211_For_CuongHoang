package utils;

import constants.Constants;

/**
 * Small character helpers shared by several normalization rules.
 *
 * @author HE176322
 */
public final class TextUtils {

    // Private constructor: every method is called through the class name.
    private TextUtils() {
    }

    // Tells whether a character counts as a space: anything Java calls whitespace (space,
    // tab, line break) plus the non-breaking space, which Java does not.
    public static boolean isSpace(char c) {
        return Character.isWhitespace(c) || c == Constants.NBSP;
    }

    // Tells whether a line has no visible character.
    public static boolean isBlank(String line) {
        // one visible character is enough to make the line not blank
        for (int i = 0; i < line.length(); i++) {
            // a visible character: not blank
            if (!isSpace(line.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // Tells whether a character is one of the brief's ", .
    public static boolean isPunctuation(char c) {
        return Constants.PUNCTUATION.indexOf(c) >= 0;
    }

    // Removes the spaces at the end of what has been built so far.
    public static void dropTrailingSpaces(StringBuilder out) {
        // cut the last character while it is a space
        while (out.length() > 0 && out.charAt(out.length() - 1) == Constants.SPACE) {
            out.setLength(out.length() - 1);
        }
    }

    // Finds the first position at or after from that is not a space.
    public static int skipSpaces(String text, int from) {
        int i = from;
        // move right while the character is a space
        while (i < text.length() && text.charAt(i) == Constants.SPACE) {
            i++;
        }
        return i;
    }
}
