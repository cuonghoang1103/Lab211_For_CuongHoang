package service;

/**
 * Rule "other words are in lower case": the whole text goes to lower case; the capitals
 * the brief does want are put back by FirstLetterUpperRule and SentenceCapitalRule, which
 * run later.
 *
 * @author HE176322
 */
public class LowerCaseRule implements INormalizeRule {

    // Creates the rule.
    public LowerCaseRule() {
    }

    // Lowers every character.
    @Override
    public String apply(String text) {
        StringBuilder builder = new StringBuilder(text.length());

        // lower each character on its own
        for (int i = 0; i < text.length(); i++) {
            builder.append(Character.toLowerCase(text.charAt(i)));
        }

        return builder.toString();
    }
}
