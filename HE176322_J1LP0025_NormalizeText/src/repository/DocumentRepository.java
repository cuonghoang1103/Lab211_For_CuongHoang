package repository;

import constants.Constants;
import java.util.ArrayList;
import model.TextDocument;
import utils.FileUtils;

/**
 * REPOSITORY: the data side of the program - loads the document from input.txt and saves
 * the result into output.txt (the brief's "read text file (input.txt) ...
 *
 * @author HE176322
 */
public class DocumentRepository implements IDocumentRepository {

    // Creates the repository.
    public DocumentRepository() {
    }

    // Writes Constants.SAMPLE_LINES to input.txt.
    @Override
    public ArrayList<String> createSample() throws Exception {
        ArrayList<String> lines = new ArrayList<>();
        // copy the constant sample into a list FileUtils can write
        for (String line : Constants.SAMPLE_LINES) {
            lines.add(line);
        }
        FileUtils.writeLines(Constants.INPUT_FILE, lines);
        return lines;
    }

    // Reads input.txt into a document.
    @Override
    public TextDocument loadDocument() throws Exception {
        return new TextDocument(FileUtils.readLines(Constants.INPUT_FILE));
    }

    // Writes the normalized text into output.txt, replacing its content.
    @Override
    public void saveDocument(TextDocument document) throws Exception {
        ArrayList<String> lines = new ArrayList<>();
        lines.add(document.getNormalizedText());
        FileUtils.writeLines(Constants.OUTPUT_FILE, lines);
    }

    // Reads output.txt back - the only proof the text really reached the disk.
    @Override
    public ArrayList<String> readOutput() throws Exception {
        return FileUtils.readLines(Constants.OUTPUT_FILE);
    }
}
