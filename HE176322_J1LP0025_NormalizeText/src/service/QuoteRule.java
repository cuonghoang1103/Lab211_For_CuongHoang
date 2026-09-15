package service;

import constants.Constants;
import utils.TextUtils;

/**
 * Rule "There are no spaces before and after sentence or word phrases in quotes": the
 * spaces JUST INSIDE the quotation marks go away - "the " second row " is" becomes "the
 * "second row" is".
 *
 * @author HE176322
 */
public class QuoteRule implements NormalizeRule {

    // Creates the rule.
    public QuoteRule() {
    }

    // Removes the spaces after an opening mark and before a closing mark.
    @Override
    public String apply(String text) {
        StringBuilder out = new StringBuilder();
        boolean insideStraight = false;
        int i = 0;
        // walk the text once
        while (i < text.length()) {
            char c = text.charAt(i);
            boolean opening = c == Constants.OPEN_QUOTE
                    || (c == Constants.STRAIGHT_QUOTE && !insideStraight);
            boolean closing = c == Constants.CLOSE_QUOTE
                    || (c == Constants.STRAIGHT_QUOTE && insideStraight);
            // a straight quote switches between "outside" and "inside"
            if (c == Constants.STRAIGHT_QUOTE) {
                insideStraight = !insideStraight;
            }
            // closing mark: no space in front of it
            if (closing) {
                TextUtils.dropTrailingSpaces(out);
            }
            out.append(c);
            i++;
            // opening mark: no space behind it
            if (opening) {
                i = TextUtils.skipSpaces(text, i);
            }
        }
        return out.toString();
    }
}
