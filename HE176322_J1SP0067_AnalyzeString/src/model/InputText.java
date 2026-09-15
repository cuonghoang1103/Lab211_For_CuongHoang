package model;

/**
 * MODEL: the string the user typed - the object this program analyses.
 *
 * @author HE176322
 */
public class InputText {

    // The text typed by the user.
    private String text;

    // JavaBean constructor: an empty text.
    public InputText() {
        this.text = "";
    }

    // Wraps the typed text.
    public InputText(String text) {
        this.text = text;
    }

    // Returns the text.
    public String getText() {
        return text;
    }

    // Replaces the text.
    public void setText(String text) {
        this.text = text;
    }

    // Number of characters of the text (the brief's first requirement).
    public int getLength() {
        return text.length();
    }

    // Polymorphism: overrides Object.toString() to return the text itself.
    @Override
    public String toString() {
        return text;
    }
}
