package service;

import constants.Constants;

/**
 * Rule "First character of word after dot is in Uppercase": the first letter after a dot
 * (or ?
 *
 * @author HE176322
 */
public class SentenceCapitalRule implements NormalizeRule {

    // Creates the rule.
    public SentenceCapitalRule() {
    }

    // Raises the first letter of every sentence after the first one.
    @Override
    public String apply(String text) {
        StringBuilder out = new StringBuilder(text);
        boolean afterDot = false;
        // one flag walks the text: "a sentence just ended"
        for (int i = 0; i < out.length(); i++) {
            char c = out.charAt(i);
            // first letter after the end of a sentence: raise it
            if (afterDot && Character.isLetter(c)) {
                out.setCharAt(i, Character.toUpperCase(c));
                afterDot = false;
            } else if (Constants.TERMINATORS.indexOf(c) >= 0 && !isDecimalPoint(out, i)) {
                // a real end of sentence: the next letter must be raised
                afterDot = true;
            }
        }
        return out.toString();
    }

    // Tells whether the character at index is a dot between two digits.
    private boolean isDecimalPoint(StringBuilder text, int index) {
        return text.charAt(index) == Constants.DOT && index > 0
                && index + 1 < text.length()
                && Character.isDigit(text.charAt(index - 1))
                && Character.isDigit(text.charAt(index + 1));
    }
}
