package repository;

import model.Content;

/**
 * REPOSITORY: holds the data of the program - the content the user typed (the model) - and
 * only simple CRUD on it. No counting, no print.
 *
 * @author HE176322
 */
public class ContentRepository {

    // The content the program works on (the model).
    private Content content;

    // Creates the store with an empty content.
    public ContentRepository() {
        content = new Content();
    }

    // Create: wraps the typed text in the model and keeps it.
    public void saveContent(String text) {
        content = new Content(text);
    }

    // Read: returns the content kept by the last save.
    public Content getContent() {
        return content;
    }
}
