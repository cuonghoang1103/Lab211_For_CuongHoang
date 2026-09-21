package dto;

import java.util.ArrayList;

/**
 * DTO carrying the answer of one menu option FROM the controller OUT TO the view - a
 * JavaBean. Each option fills its own fields; the view prints only what is not null.
 *
 * @author HE176322
 */
public class NormalizeResponseDTO {

    // Option 1: the lines written to input.txt (the view counts them).
    private ArrayList<String> sampleLineList;

    // Option 2: the lines of input.txt exactly as main read them (the BEFORE panel).
    private ArrayList<String> inputLineList;

    // Option 3: the lines of output.txt as main read them back from the disk.
    private ArrayList<String> outputLineList;

    // Option 4: the line as the user typed it.
    private String typedText;

    // Options 2 and 4: the text after every rule (the AFTER panel, or OUT).
    private String normalizedText;

    // Option 5: the sample cases, each with its title and its text before and after.
    private ArrayList<CaseResponseDTO> caseList;

    // JavaBean constructor: an empty answer, filled through the setters.
    public NormalizeResponseDTO() {
    }

    // Returns the lines written to input.txt.
    public ArrayList<String> getSampleLineList() {
        return sampleLineList;
    }

    // Sets the lines written to input.txt.
    public void setSampleLineList(ArrayList<String> sampleLineList) {
        this.sampleLineList = sampleLineList;
    }

    // Returns the lines of input.txt.
    public ArrayList<String> getInputLineList() {
        return inputLineList;
    }

    // Sets the lines of input.txt.
    public void setInputLineList(ArrayList<String> inputLineList) {
        this.inputLineList = inputLineList;
    }

    // Returns the lines of output.txt.
    public ArrayList<String> getOutputLineList() {
        return outputLineList;
    }

    // Sets the lines of output.txt.
    public void setOutputLineList(ArrayList<String> outputLineList) {
        this.outputLineList = outputLineList;
    }

    // Returns the typed line.
    public String getTypedText() {
        return typedText;
    }

    // Sets the typed line.
    public void setTypedText(String typedText) {
        this.typedText = typedText;
    }

    // Returns the normalized text.
    public String getNormalizedText() {
        return normalizedText;
    }

    // Sets the normalized text.
    public void setNormalizedText(String normalizedText) {
        this.normalizedText = normalizedText;
    }

    // Returns the sample cases.
    public ArrayList<CaseResponseDTO> getCaseList() {
        return caseList;
    }

    // Sets the sample cases.
    public void setCaseList(ArrayList<CaseResponseDTO> caseList) {
        this.caseList = caseList;
    }
}
