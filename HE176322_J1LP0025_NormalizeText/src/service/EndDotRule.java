package service;

import constants.Constants;

/**
 * Rule "Must have dot at the end of text".
 *
 * @author HE176322
 */
public class EndDotRule implements INormalizeRule {

    // Creates the rule.
    public EndDotRule() {
    }

    // Makes the text end with a dot.
    @Override
    public String apply(String text) {
        StringBuilder builder = new StringBuilder(text);
        char last = Constants.SPACE;

        // nothing to end
        if (text.isEmpty()) {
            return text;
        }

        // the last character decides
        last = text.charAt(text.length() - 1);

        // already ends a sentence
        if (Constants.TERMINATORS.indexOf(last) >= 0) {
            return text;
        }

        // a comma or colon at the end is replaced by the dot
        if ((last == Constants.COMMA) || (last == Constants.COLON)) {
            builder.setLength(builder.length() - 1);
        }

        // the dot the brief wants at the end of the text
        builder.append(Constants.DOT);
        return builder.toString();
    }
}
