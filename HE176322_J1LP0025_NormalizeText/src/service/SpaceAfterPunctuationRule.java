package service;

import constants.Constants;
import utils.TextUtils;

/**
 * Rule "Only one space after comma (,), dot (.) and colon (:)": "one,two" becomes "one,
 * two" and "one, two" becomes "one, two".
 *
 * @author HE176322
 */
public class SpaceAfterPunctuationRule implements NormalizeRule {

    // Creates the rule.
    public SpaceAfterPunctuationRule() {
    }

    // Leaves exactly one space (or none, see the class comment) after every comma, dot
    // and colon.
    @Override
    public String apply(String text) {
        StringBuilder out = new StringBuilder();
        int i = 0;
        // walk the text; after a mark, jump over the spaces that follow it
        while (i < text.length()) {
            char c = text.charAt(i);
            out.append(c);
            // an ordinary character: just copy it
            if (!TextUtils.isPunctuation(c)) {
                i++;
                continue;
            }
            int next = TextUtils.skipSpaces(text, i + 1);
            // something follows the mark: decide whether one space goes between
            if (next < text.length() && isSpaceNeeded(out, text.charAt(next))) {
                out.append(Constants.SPACE);
            }
            i = next;
        }
        return out.toString();
    }

    // Decides whether a space goes between the mark just written and the next visible
    // character.
    private boolean isSpaceNeeded(StringBuilder out, char after) {
        char mark = out.charAt(out.length() - 1);
        boolean decimalPoint = mark == Constants.DOT && Character.isDigit(after)
                && out.length() >= 2 && Character.isDigit(out.charAt(out.length() - 2));
        boolean glued = TextUtils.isPunctuation(after) || after == Constants.CLOSE_QUOTE
                || after == Constants.STRAIGHT_QUOTE;
        return !decimalPoint && !glued;
    }
}
