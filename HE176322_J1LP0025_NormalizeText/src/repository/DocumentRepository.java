package repository;

import constants.Constants;
import java.util.ArrayList;
import java.util.Arrays;
import model.TextDocument;
import utils.FileUtils;

/**
 * REPOSITORY: holds the data of the program - the document being normalized (the lines
 * main read from input.txt or the keyboard, then its normalized text) - and only simple
 * CRUD on it. Writing input.txt / output.txt goes to the disk through FileUtils. No rule,
 * no print, no reading of files.
 *
 * @author HE176322
 */
public class DocumentRepository implements IDocumentRepository {

    // The document the program is working on (the model).
    private TextDocument document;

    // Creates the store with an empty document.
    public DocumentRepository() {
        document = new TextDocument();
    }

    // Create: keeps the lines main read as the current document, replacing the old one.
    @Override
    public void saveDocument(ArrayList<String> lineList) {
        document = new TextDocument(lineList);
    }

    // Read: returns the current document.
    @Override
    public TextDocument getDocument() {
        return document;
    }

    // Create (on the disk): writes Constants.SAMPLE_LINE_ARRAY to input.txt; the writing
    // itself is FileUtils' job ("Error: Cannot write the file" comes from there).
    @Override
    public ArrayList<String> saveSampleFile() throws Exception {
        ArrayList<String> lineList = new ArrayList<>(Arrays.asList(Constants.SAMPLE_LINE_ARRAY));

        // one element of the list = one line of input.txt
        FileUtils.writeLines(Constants.INPUT_FILE, lineList);
        return lineList;
    }

    // Update (on the disk): writes the normalized text of the current document into
    // output.txt, replacing its content.
    @Override
    public void saveOutputFile() throws Exception {
        ArrayList<String> lineList = new ArrayList<>();

        // the normalized document is one line of text
        lineList.add(document.getNormalizedText());
        FileUtils.writeLines(Constants.OUTPUT_FILE, lineList);
    }
}
