package service;

/**
 * Rule "First character of word in first line is in Uppercase": the first LETTER of the
 * text is raised - not blindly character 0, because the text may start with a quotation
 * mark.
 *
 * @author HE176322
 */
public class FirstLetterUpperRule implements INormalizeRule {

    // Creates the rule.
    public FirstLetterUpperRule() {
    }

    // Raises the first letter of the text.
    @Override
    public String apply(String text) {
        StringBuilder builder = new StringBuilder(text);

        // find the first letter
        for (int i = 0; i < builder.length(); i++) {
            // the first letter: raise it and stop
            if (Character.isLetter(builder.charAt(i))) {
                builder.setCharAt(i, Character.toUpperCase(builder.charAt(i)));
                break;
            }
        }

        return builder.toString();
    }
}
