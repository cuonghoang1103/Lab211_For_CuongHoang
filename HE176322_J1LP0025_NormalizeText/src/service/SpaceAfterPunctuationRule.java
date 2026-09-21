package service;

import constants.Constants;
import utils.TextUtils;

/**
 * Rule "Only one space after comma (,), dot (.) and colon (:)": "one,two" and "one,   two"
 * both become "one, two". No space goes after a decimal point (3.14), nor before another
 * mark or a closing quote.
 *
 * @author HE176322
 */
public class SpaceAfterPunctuationRule implements INormalizeRule {

    // Creates the rule.
    public SpaceAfterPunctuationRule() {
    }

    // Leaves exactly one space (or none, see the class comment) after every comma, dot
    // and colon.
    @Override
    public String apply(String text) {
        StringBuilder builder = new StringBuilder();
        int index = 0;

        // walk the text; after a mark, jump over the spaces that follow it
        while (index < text.length()) {
            char character = text.charAt(index);
            int next = 0;

            // copy the character
            builder.append(character);

            // an ordinary character: just go on
            if (!TextUtils.isPunctuation(character)) {
                index++;
                continue;
            }

            // a mark: find the next visible character after it
            next = TextUtils.findNonSpace(text, index + 1);

            // something follows the mark: decide whether one space goes between
            if ((next < text.length()) && isSpaceNeeded(builder, text.charAt(next))) {
                builder.append(Constants.SPACE);
            }

            // go on from the next visible character
            index = next;
        }

        return builder.toString();
    }

    // Decides whether a space goes between the mark just written and the next visible
    // character.
    private boolean isSpaceNeeded(StringBuilder builder, char after) {
        char mark = builder.charAt(builder.length() - 1);
        boolean decimalPoint = (mark == Constants.DOT) && Character.isDigit(after) &&
                (builder.length() >= 2) &&
                Character.isDigit(builder.charAt(builder.length() - 2));
        boolean glued = TextUtils.isPunctuation(after) || (after == Constants.CLOSE_QUOTE) ||
                (after == Constants.STRAIGHT_QUOTE);

        // a decimal point, or a mark glued to the next one, takes no space
        return !decimalPoint && !glued;
    }
}
