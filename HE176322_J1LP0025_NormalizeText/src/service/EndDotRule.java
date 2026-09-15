package service;

import constants.Constants;

/**
 * Rule "Must have dot at the end of text".
 *
 * @author HE176322
 */
public class EndDotRule implements NormalizeRule {

    // Creates the rule.
    public EndDotRule() {
    }

    // Makes the text end with a dot.
    @Override
    public String apply(String text) {
        // nothing to end
        if (text.isEmpty()) {
            return text;
        }
        char last = text.charAt(text.length() - 1);
        // already ends a sentence
        if (Constants.TERMINATORS.indexOf(last) >= 0) {
            return text;
        }
        // a comma or colon at the end becomes the dot
        if (last == Constants.COMMA || last == Constants.COLON) {
            return text.substring(0, text.length() - 1) + Constants.DOT;
        }
        return text + Constants.DOT;
    }
}
