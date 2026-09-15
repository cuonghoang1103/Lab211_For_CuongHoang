package service;

import utils.TextUtils;

/**
 * Rule "There are no space between comma or dot and word in front of it" (applied to the
 * colon too): "word ," becomes "word,".
 *
 * @author HE176322
 */
public class NoSpaceBeforePunctuationRule implements NormalizeRule {

    // Creates the rule.
    public NoSpaceBeforePunctuationRule() {
    }

    // Pulls every ", .
    @Override
    public String apply(String text) {
        StringBuilder out = new StringBuilder();
        // copy the text, removing the spaces written just before a mark
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            // a comma, dot or colon: the spaces in front of it go away
            if (TextUtils.isPunctuation(c)) {
                TextUtils.dropTrailingSpaces(out);
            }
            out.append(c);
        }
        return out.toString();
    }
}
