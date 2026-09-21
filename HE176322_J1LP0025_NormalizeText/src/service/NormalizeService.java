package service;

import constants.Message;
import dto.CaseResponseDTO;
import dto.NormalizeRequestDTO;
import java.util.ArrayList;
import model.TextDocument;
import repository.IDocumentRepository;

/**
 * SERVICE and Strategy CONTEXT: runs a text through the ordered list of normalization
 * rules. The text comes from the repository (the document main read), and the result goes
 * back into it before output.txt is written.
 *
 * @author HE176322
 */
public class NormalizeService {

    // Where the document is kept and the files are written.
    private IDocumentRepository documentRepository;

    // The rules, in the order they must run (the strategies).
    private ArrayList<INormalizeRule> ruleList;

    // Creates the service with its storage and its ordered rules.
    public NormalizeService(IDocumentRepository documentRepository,
            ArrayList<INormalizeRule> ruleList) {
        this.documentRepository = documentRepository;
        this.ruleList = ruleList;
    }

    // Option 1: writes the sample input file; returns its lines.
    public ArrayList<String> createSample() throws Exception {
        return documentRepository.saveSampleFile();
    }

    // Option 2, the brief's whole job: keeps the lines main read from input.txt,
    // normalizes them, writes output.txt; returns the normalized text.
    public String normalizeFile(NormalizeRequestDTO requestDTO) throws Exception {
        String normalizedText = "";

        // keep the document, run the rules on it, then write the result
        documentRepository.saveDocument(requestDTO.getLineList());
        normalizedText = normalizeDocument();
        documentRepository.saveOutputFile();
        return normalizedText;
    }

    // Option 4: the typed line is a document of one line, normalized by the same rules;
    // returns the result.
    public String normalizeText(NormalizeRequestDTO requestDTO) {
        ArrayList<String> lineList = new ArrayList<>();

        // keep the typed line as the document, then run the rules on it
        lineList.add(requestDTO.getText());
        documentRepository.saveDocument(lineList);
        return normalizeDocument();
    }

    // Option 5: normalizes every sample case of Message.SAMPLE_CASE_ARRAY.
    public ArrayList<CaseResponseDTO> normalizeCases() {
        ArrayList<CaseResponseDTO> caseList = new ArrayList<>();

        // each case is {title, input}
        for (String[] sampleArray : Message.SAMPLE_CASE_ARRAY) {
            caseList.add(new CaseResponseDTO(sampleArray[0], sampleArray[1],
                    normalize(sampleArray[1])));
        }

        return caseList;
    }

    // Runs the current document of the repository through every rule and keeps the
    // result in it.
    private String normalizeDocument() {
        TextDocument document = documentRepository.getDocument();

        // the rules work on the whole document as one text
        document.setNormalizedText(normalize(document.getFullText()));
        return document.getNormalizedText();
    }

    // Runs the text through every rule, in order: the output of one rule is the input of
    // the next.
    private String normalize(String text) {
        String result = text;

        // apply the rules one after the other
        for (INormalizeRule rule : ruleList) {
            result = rule.apply(result);
        }

        return result;
    }
}
