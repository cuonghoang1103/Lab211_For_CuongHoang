package service;

import constants.Constants;
import utils.TextUtils;

/**
 * Rule "There are no spaces before and after sentence or word phrases in quotes": the
 * spaces JUST INSIDE the quotation marks go away - the “ second row ” is becomes the
 * “second row” is (straight quotes " too).
 *
 * @author HE176322
 */
public class QuoteRule implements INormalizeRule {

    // Creates the rule.
    public QuoteRule() {
    }

    // Removes the spaces after an opening mark and before a closing mark.
    @Override
    public String apply(String text) {
        StringBuilder builder = new StringBuilder();
        boolean insideStraight = false;
        int index = 0;

        // walk the text once
        while (index < text.length()) {
            char character = text.charAt(index);
            boolean opening = (character == Constants.OPEN_QUOTE) ||
                    ((character == Constants.STRAIGHT_QUOTE) && !insideStraight);
            boolean closing = (character == Constants.CLOSE_QUOTE) ||
                    ((character == Constants.STRAIGHT_QUOTE) && insideStraight);

            // a straight quote switches between "outside" and "inside"
            if (character == Constants.STRAIGHT_QUOTE) {
                insideStraight = !insideStraight;
            }

            // closing mark: no space in front of it
            if (closing) {
                TextUtils.removeTrailingSpaces(builder);
            }

            // copy the character and step over it
            builder.append(character);
            index++;

            // opening mark: no space behind it
            if (opening) {
                index = TextUtils.findNonSpace(text, index);
            }
        }

        return builder.toString();
    }
}
