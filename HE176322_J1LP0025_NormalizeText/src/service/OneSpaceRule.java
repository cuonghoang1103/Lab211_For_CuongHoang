package service;

import constants.Constants;
import utils.TextUtils;

/**
 * Rule "Only one space between words": every run of spaces, tabs or non-breaking spaces
 * becomes ONE space, and the ends of the text are trimmed.
 *
 * @author HE176322
 */
public class OneSpaceRule implements INormalizeRule {

    // Creates the rule.
    public OneSpaceRule() {
    }

    // Collapses spaces.
    @Override
    public String apply(String text) {
        StringBuilder builder = new StringBuilder();
        boolean pendingSpace = false;

        // decide each character once
        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);

            // a space: remember it, but not at the very beginning
            if (TextUtils.isSpace(character)) {
                pendingSpace = (builder.length() > 0);
            } else {
                // a real character: write the remembered space first
                if (pendingSpace) {
                    builder.append(Constants.SPACE);
                    pendingSpace = false;
                }

                // then the character itself
                builder.append(character);
            }
        }

        return builder.toString();
    }
}
