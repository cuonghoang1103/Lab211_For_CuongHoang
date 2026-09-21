package service;

import constants.Constants;

/**
 * Rule "First character of word after dot is in Uppercase": the first letter after a dot
 * (or after ? and !, see Constants.TERMINATORS) is raised; a decimal point such as 3.14
 * does not end a sentence.
 *
 * @author HE176322
 */
public class SentenceCapitalRule implements INormalizeRule {

    // Creates the rule.
    public SentenceCapitalRule() {
    }

    // Raises the first letter of every sentence after the first one.
    @Override
    public String apply(String text) {
        StringBuilder builder = new StringBuilder(text);
        boolean afterDot = false;

        // one flag walks the text: "a sentence just ended"
        for (int i = 0; i < builder.length(); i++) {
            char character = builder.charAt(i);

            // first letter after the end of a sentence: raise it
            if (afterDot && Character.isLetter(character)) {
                builder.setCharAt(i, Character.toUpperCase(character));
                afterDot = false;
            } else if (isSentenceEnd(builder, i)) {
                // a real end of sentence: the next letter must be raised
                afterDot = true;
            }
        }

        return builder.toString();
    }

    // Tells whether the character at index ends a sentence: a dot, ? or ! that is not a
    // decimal point.
    private boolean isSentenceEnd(StringBuilder builder, int index) {
        return (Constants.TERMINATORS.indexOf(builder.charAt(index)) >= 0) &&
                !isDecimalPoint(builder, index);
    }

    // Tells whether the character at index is a dot between two digits (3.14).
    private boolean isDecimalPoint(StringBuilder builder, int index) {
        return (builder.charAt(index) == Constants.DOT) && (index > 0) &&
                (index < (builder.length() - 1)) &&
                Character.isDigit(builder.charAt(index - 1)) &&
                Character.isDigit(builder.charAt(index + 1));
    }
}
