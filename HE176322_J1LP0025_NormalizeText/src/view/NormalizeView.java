package view;

import constants.Constants;
import constants.Message;
import dto.CaseResponseDTO;
import dto.NormalizeResponseDTO;
import java.util.ArrayList;

/**
 * VIEW: the only place (with main) allowed to print results. It receives the data through
 * its attribute (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class NormalizeView {

    // The answer to print, handed over by the controller.
    private NormalizeResponseDTO responseDTO;

    // Receives the answer the next display() call will print.
    public void setResponseDTO(NormalizeResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints what the controller set - each menu option fills its own part of the answer.
    public void display() {
        // option 1: "Sample input written to input.txt (7 lines)."
        if (responseDTO.getSampleLineList() != null) {
            System.out.println(String.format(Message.SAMPLE_WRITTEN, Constants.INPUT_FILE,
                    formatLineCount(responseDTO.getSampleLineList().size())));
        }

        // option 2: the BEFORE panel, the AFTER panel and the confirmation
        if (responseDTO.getInputLineList() != null) {
            displayNormalized();
        }

        // option 3: output.txt as read back from the disk
        if (responseDTO.getOutputLineList() != null) {
            displayOutputFile();
        }

        // option 4: the typed line before and after
        if (responseDTO.getTypedText() != null) {
            System.out.println(String.format(Message.TYPED_IN,
                    makeVisible(responseDTO.getTypedText())));
            System.out.println(String.format(Message.TYPED_OUT,
                    makeVisible(responseDTO.getNormalizedText())));
        }

        // option 5: every sample case, numbered, before and after
        if (responseDTO.getCaseList() != null) {
            displayCases();
        }
    }

    // Option 2: the before panel (numbered raw lines), the after panel (the normalized
    // text) and the confirmation.
    private void displayNormalized() {
        ArrayList<String> lineList = responseDTO.getInputLineList();

        // the title of the BEFORE panel
        System.out.println(Message.RULE);
        System.out.println(String.format(Message.TITLE_BEFORE, Constants.INPUT_FILE,
                formatLineCount(lineList.size())));
        System.out.println(Message.RULE);

        // number every raw line, from 1
        for (int i = 0; i < lineList.size(); i++) {
            System.out.println(String.format(Message.RAW_LINE, i + 1,
                    makeVisible(lineList.get(i))));
        }

        // the AFTER panel: the one normalized line, then the confirmation
        System.out.println(Message.RULE);
        System.out.println(String.format(Message.TITLE_AFTER, Constants.OUTPUT_FILE,
                formatLineCount(1)));
        System.out.println(Message.RULE);
        System.out.println(responseDTO.getNormalizedText());
        System.out.println(Message.RULE);
        System.out.println(String.format(Message.NORMALIZED_WRITTEN, Constants.OUTPUT_FILE));
    }

    // Option 3: output.txt as read back from the disk.
    private void displayOutputFile() {
        ArrayList<String> lineList = responseDTO.getOutputLineList();

        // the title of the ON DISK panel
        System.out.println(Message.RULE);
        System.out.println(String.format(Message.TITLE_ON_DISK, Constants.OUTPUT_FILE,
                formatLineCount(lineList.size())));
        System.out.println(Message.RULE);

        // print the file line by line
        for (String line : lineList) {
            System.out.println(line);
        }

        // close the panel
        System.out.println(Message.RULE);
    }

    // Option 5: every sample case, numbered, before and after.
    private void displayCases() {
        ArrayList<CaseResponseDTO> caseList = responseDTO.getCaseList();

        // the title of the panel
        System.out.println(Message.RULE);
        System.out.println(Message.TITLE_CASES);
        System.out.println(Message.RULE);

        // one numbered block per case
        for (int i = 0; i < caseList.size(); i++) {
            CaseResponseDTO sample = caseList.get(i);

            // its number and title, then the text before and after
            System.out.println(String.format(Message.CASE_TITLE, i + 1, sample.getTitle()));
            System.out.println(String.format(Message.CASE_IN, makeVisible(sample.getInput())));
            System.out.println(String.format(Message.CASE_OUT,
                    makeVisible(sample.getOutput())));
        }

        // close the panel
        System.out.println(Message.RULE);
    }

    // Writes a line count as "1 line" or "7 lines".
    private String formatLineCount(int count) {
        // singular only for exactly one line
        if (count == 1) {
            return String.format(Message.ONE_LINE, count);
        }

        return String.format(Message.MANY_LINES, count);
    }

    // Makes the invisible characters visible: a tab becomes \t and a non-breaking space
    // becomes <nbsp> (Constants.VISIBLE_TAB, Constants.VISIBLE_NBSP).
    private String makeVisible(String text) {
        StringBuilder builder = new StringBuilder();

        // decide each character once
        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);

            // replace the two invisible characters, copy the rest
            switch (character) {
                // a tab
                case Constants.TAB:
                    builder.append(Constants.VISIBLE_TAB);
                    break;

                // a non-breaking space
                case Constants.NBSP:
                    builder.append(Constants.VISIBLE_NBSP);
                    break;

                // any other character is shown as it is
                default:
                    builder.append(character);
                    break;
            }
        }

        return builder.toString();
    }
}
