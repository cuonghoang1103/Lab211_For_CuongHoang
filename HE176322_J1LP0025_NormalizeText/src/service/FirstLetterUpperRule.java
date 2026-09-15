package service;

/**
 * Rule "First character of word in first line is in Uppercase": the first LETTER of the
 * text is raised - not blindly character 0, because the text may start with a quotation
 * mark.
 *
 * @author HE176322
 */
public class FirstLetterUpperRule implements NormalizeRule {

    // Creates the rule.
    public FirstLetterUpperRule() {
    }

    // Raises the first letter of the text.
    @Override
    public String apply(String text) {
        StringBuilder out = new StringBuilder(text);
        // find the first letter
        for (int i = 0; i < out.length(); i++) {
            // the first letter: raise it and stop
            if (Character.isLetter(out.charAt(i))) {
                out.setCharAt(i, Character.toUpperCase(out.charAt(i)));
                break;
            }
        }
        return out.toString();
    }
}
