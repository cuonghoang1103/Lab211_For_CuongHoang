package service;

import constants.Constants;
import utils.TextUtils;

/**
 * Rule "There are no blank line between lines": drops every blank line and joins the
 * remaining lines into one text, separated by a space (the brief's example turns its two
 * paragraphs into one).
 *
 * @author HE176322
 */
public class RemoveBlankLineRule implements NormalizeRule {

    // Creates the rule.
    public RemoveBlankLineRule() {
    }

    // Drops blank lines, joins the others with one space.
    @Override
    public String apply(String text) {
        StringBuilder out = new StringBuilder();
        // look at every line of the text
        for (String line : text.split(Constants.LINE_BREAK)) {
            // an empty line, or only spaces and tabs: contributes nothing
            if (TextUtils.isBlank(line)) {
                continue;
            }
            // not the first kept line: glue it to the previous one
            if (out.length() > 0) {
                out.append(Constants.SPACE);
            }
            out.append(line);
        }
        return out.toString();
    }
}
