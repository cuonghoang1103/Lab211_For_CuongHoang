package repository;

import java.util.ArrayList;
import model.TextDocument;

/**
 * The storage contract of the documents (Dependency Inversion): the service depends on
 * this interface, not on the text files behind it.
 *
 * @author HE176322
 */
public interface IDocumentRepository {

    // Writes the untidy sample document to the input file.
    ArrayList<String> createSample() throws Exception;

    // Reads the input file into a document.
    TextDocument loadDocument() throws Exception;

    // Writes the normalized text of the document to the output file.
    void saveDocument(TextDocument document) throws Exception;

    // Reads the output file back from the disk.
    ArrayList<String> readOutput() throws Exception;
}
