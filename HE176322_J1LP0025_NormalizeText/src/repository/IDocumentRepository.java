package repository;

import java.util.ArrayList;
import model.TextDocument;

/**
 * The storage contract of the documents (Dependency Inversion): the service depends on
 * this interface, not on the class and the text files behind it.
 *
 * @author HE176322
 */
public interface IDocumentRepository {

    // Keeps the lines main read as the current document.
    void saveDocument(ArrayList<String> lineList);

    // Returns the current document.
    TextDocument getDocument();

    // Writes the untidy sample document to the input file; returns its lines.
    ArrayList<String> saveSampleFile() throws Exception;

    // Writes the normalized text of the current document to the output file.
    void saveOutputFile() throws Exception;
}
