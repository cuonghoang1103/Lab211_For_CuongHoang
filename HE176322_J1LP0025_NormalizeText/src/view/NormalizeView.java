package view;

import constants.Constants;
import constants.Message;
import dto.CaseResponseDTO;
import dto.DocumentResponseDTO;
import java.util.ArrayList;

/**
 * VIEW: the only place (with main) allowed to print results.
 *
 * @author HE176322
 */
public class NormalizeView {

    // The document to show (options 1, 2, 3).
    private DocumentResponseDTO document;
    // The typed line before/after (option 4).
    private CaseResponseDTO oneCase;
    // The sample cases (option 5).
    private ArrayList<CaseResponseDTO> cases;

    // Receives the document the next display call will print.
    public void setDocument(DocumentResponseDTO document) {
        this.document = document;
    }

    // Receives the typed line before and after.
    public void setCase(CaseResponseDTO oneCase) {
        this.oneCase = oneCase;
    }

    // Receives the sample cases.
    public void setCases(ArrayList<CaseResponseDTO> cases) {
        this.cases = cases;
    }

    // Option 1: "Sample input written to input.txt (7 lines).".
    public void displaySampleCreated() {
        System.out.println(String.format(Message.SAMPLE_WRITTEN, Constants.INPUT_FILE,
                countLines(document.getLines().size())));
    }

    // Option 2: the before panel (numbered raw lines), the after panel (the normalized
    // text) and the confirmation.
    public void displayNormalized() {
        ArrayList<String> lines = document.getLines();
        System.out.println(Message.RULE);
        System.out.println(String.format(Message.TITLE_BEFORE, Constants.INPUT_FILE,
                countLines(lines.size())));
        System.out.println(Message.RULE);
        // number every raw line, from 1
        for (int i = 0; i < lines.size(); i++) {
            System.out.println(String.format(Message.RAW_LINE, i + 1, visible(lines.get(i))));
        }
        System.out.println(Message.RULE);
        System.out.println(String.format(Message.TITLE_AFTER, Constants.OUTPUT_FILE,
                countLines(1)));
        System.out.println(Message.RULE);
        System.out.println(document.getNormalizedText());
        System.out.println(Message.RULE);
        System.out.println(String.format(Message.NORMALIZED_WRITTEN, Constants.OUTPUT_FILE));
    }

    // Option 3: output.txt as read back from the disk.
    public void displayOutputFile() {
        ArrayList<String> lines = document.getLines();
        System.out.println(Message.RULE);
        System.out.println(String.format(Message.TITLE_ON_DISK, Constants.OUTPUT_FILE,
                countLines(lines.size())));
        System.out.println(Message.RULE);
        // print the file line by line
        for (String line : lines) {
            System.out.println(line);
        }
        System.out.println(Message.RULE);
    }

    // Option 4: the typed line before and after.
    public void displayCase() {
        System.out.println(String.format(Message.TYPED_IN, visible(oneCase.getInput())));
        System.out.println(String.format(Message.TYPED_OUT, visible(oneCase.getOutput())));
    }

    // Option 5: every sample case, numbered, before and after.
    public void displayCases() {
        System.out.println(Message.RULE);
        System.out.println(Message.TITLE_CASES);
        System.out.println(Message.RULE);
        // one numbered block per case
        for (int i = 0; i < cases.size(); i++) {
            CaseResponseDTO sample = cases.get(i);
            System.out.println(String.format(Message.CASE_TITLE, i + 1, sample.getTitle()));
            System.out.println(String.format(Message.CASE_IN, visible(sample.getInput())));
            System.out.println(String.format(Message.CASE_OUT, visible(sample.getOutput())));
        }
        System.out.println(Message.RULE);
    }

    // "1 line" or "7 lines".
    private String countLines(int count) {
        // singular only for exactly one line
        if (count == 1) {
            return String.format(Message.ONE_LINE, count);
        }
        return String.format(Message.MANY_LINES, count);
    }

    // Makes invisible characters visible: a tab becomes \t and a non-breaking space
    // becomes .
    private String visible(String text) {
        StringBuilder out = new StringBuilder();
        // decide each character once
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            // replace the two invisible characters, copy the rest
            switch (c) {
                // a tab
                case Constants.TAB:
                    out.append(Constants.VISIBLE_TAB);
                    break;
                // a non-breaking space
                case Constants.NBSP:
                    out.append(Constants.VISIBLE_NBSP);
                    break;
                // any other character is shown as it is
                default:
                    out.append(c);
                    break;
            }
        }
        return out.toString();
    }
}
