package service;

import constants.Message;
import dto.CaseResponseDTO;
import dto.DocumentResponseDTO;
import dto.NormalizeRequestDTO;
import java.util.ArrayList;
import model.TextDocument;
import repository.IDocumentRepository;

/**
 * SERVICE and Strategy CONTEXT: runs a text through the ordered list of normalization
 * rules, and drives the read - normalize - write job.
 *
 * @author HE176322
 */
public class NormalizeService {

    // Where the documents are loaded from and saved to.
    private IDocumentRepository documentRepository;
    // The rules, in the order they must run (the strategies).
    private ArrayList<NormalizeRule> rules;

    // Creates the service with its storage and its ordered rules.
    public NormalizeService(IDocumentRepository documentRepository,
            ArrayList<NormalizeRule> rules) {
        this.documentRepository = documentRepository;
        this.rules = rules;
    }

    // Option 1: writes the sample input file.
    public DocumentResponseDTO createSample() throws Exception {
        return new DocumentResponseDTO(documentRepository.createSample(), "");
    }

    // Option 2, the brief's whole job: read input.txt, normalize it, write output.txt.
    public DocumentResponseDTO normalizeFile() throws Exception {
        TextDocument document = documentRepository.loadDocument();
        document.setNormalizedText(normalize(document.getFullText()));
        documentRepository.saveDocument(document);
        return new DocumentResponseDTO(document.getLines(), document.getNormalizedText());
    }

    // Option 3: reads output.txt back from the disk.
    public DocumentResponseDTO readOutput() throws Exception {
        return new DocumentResponseDTO(documentRepository.readOutput(), "");
    }

    // Option 4: normalizes one typed line with the same rules.
    public CaseResponseDTO normalizeText(NormalizeRequestDTO requestDTO) {
        return new CaseResponseDTO("", requestDTO.getText(),
                normalize(requestDTO.getText()));
    }

    // Option 5: normalizes every sample case of Message.SAMPLE_CASES.
    public ArrayList<CaseResponseDTO> normalizeCases() {
        ArrayList<CaseResponseDTO> result = new ArrayList<>();
        // each case is {title, input}
        for (String[] sample : Message.SAMPLE_CASES) {
            result.add(new CaseResponseDTO(sample[0], sample[1], normalize(sample[1])));
        }
        return result;
    }

    // Runs the text through every rule, in order: the output of one rule is the input of
    // the next.
    private String normalize(String text) {
        String result = text;
        // apply the rules one after the other
        for (NormalizeRule rule : rules) {
            result = rule.apply(result);
        }
        return result;
    }
}
