package repository;

import model.InputText;

/**
 * REPOSITORY: holds the data of the program - the string the user typed, as the model
 * InputText - and only simple CRUD on it. No analysis, no print.
 *
 * @author HE176322
 */
public class TextRepository {

    // The string the program works on (the model).
    private InputText inputText;

    // Creates the store with an empty string.
    public TextRepository() {
        inputText = new InputText();
    }

    // Create: wraps the typed string in the model and keeps it.
    public void saveInputText(String text) {
        inputText = new InputText(text);
    }

    // Read: returns the string kept by the last save.
    public InputText getInputText() {
        return inputText;
    }
}
