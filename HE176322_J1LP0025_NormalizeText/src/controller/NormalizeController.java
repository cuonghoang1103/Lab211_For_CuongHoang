package controller;

import dto.NormalizeRequestDTO;
import dto.NormalizeResponseDTO;
import java.util.ArrayList;
import repository.DocumentRepository;
import service.EndDotRule;
import service.FirstLetterUpperRule;
import service.INormalizeRule;
import service.LowerCaseRule;
import service.NoSpaceBeforePunctuationRule;
import service.NormalizeService;
import service.OneSpaceRule;
import service.QuoteRule;
import service.RemoveBlankLineRule;
import service.SentenceCapitalRule;
import service.SpaceAfterPunctuationRule;
import view.NormalizeView;

/**
 * CONTROLLER (and Facade): receives the request from main, asks the service for the
 * result, and hands the answer to the view ONCE per menu option. No Scanner, no print, no
 * model.
 *
 * @author HE176322
 */
public class NormalizeController {

    // Normalizes and drives the file job (Controller -> Service -> Repository -> Model).
    private NormalizeService normalizeService;

    // Prints the answer of each menu option.
    private NormalizeView normalizeView;

    // Creates the controller: the document store and the brief's rules.
    public NormalizeController() {
        normalizeService = new NormalizeService(new DocumentRepository(), createRuleList());
        normalizeView = new NormalizeView();
    }

    // The brief's rules in the order they must run (HUONG-DAN 2.2 says why this order).
    private ArrayList<INormalizeRule> createRuleList() {
        ArrayList<INormalizeRule> ruleList = new ArrayList<>();

        // lines and spaces first, then punctuation and quotes, the capitals and the dot last
        ruleList.add(new RemoveBlankLineRule());
        ruleList.add(new OneSpaceRule());
        ruleList.add(new LowerCaseRule());
        ruleList.add(new NoSpaceBeforePunctuationRule());
        ruleList.add(new SpaceAfterPunctuationRule());
        ruleList.add(new QuoteRule());
        ruleList.add(new FirstLetterUpperRule());
        ruleList.add(new SentenceCapitalRule());
        ruleList.add(new EndDotRule());
        return ruleList;
    }

    // Option 1: writes the sample input.txt, then the view prints "Sample input written to
    // input.txt (7 lines)." - once.
    public void createSample() throws Exception {
        NormalizeResponseDTO responseDTO = new NormalizeResponseDTO();

        // the lines written to the file (the view counts them)
        responseDTO.setSampleLineList(normalizeService.createSample());

        // hand the answer to the view, then render it - once for the whole flow
        normalizeView.setResponseDTO(responseDTO);
        normalizeView.display();
    }

    // Option 2: normalizes the lines main read from input.txt into output.txt, then the
    // view prints the BEFORE and AFTER panels and the confirmation - once.
    public void normalizeFile(NormalizeRequestDTO requestDTO) throws Exception {
        NormalizeResponseDTO responseDTO = new NormalizeResponseDTO();

        // before: the lines main read; after: the text the service wrote to output.txt
        responseDTO.setInputLineList(requestDTO.getLineList());
        responseDTO.setNormalizedText(normalizeService.normalizeFile(requestDTO));

        // hand the answer to the view, then render it - once for the whole flow
        normalizeView.setResponseDTO(responseDTO);
        normalizeView.display();
    }

    // Option 3: output.txt as main read it back from the disk - nothing to compute, the
    // lines go to the view as they are - once.
    public void showOutput(NormalizeRequestDTO requestDTO) {
        NormalizeResponseDTO responseDTO = new NormalizeResponseDTO();

        // the lines of output.txt, untouched
        responseDTO.setOutputLineList(requestDTO.getLineList());

        // hand the answer to the view, then render it - once for the whole flow
        normalizeView.setResponseDTO(responseDTO);
        normalizeView.display();
    }

    // Option 4: normalizes one typed line, then the view prints it before and after - once.
    public void normalizeText(NormalizeRequestDTO requestDTO) {
        NormalizeResponseDTO responseDTO = new NormalizeResponseDTO();

        // IN: the line as typed; OUT: the line after every rule
        responseDTO.setTypedText(requestDTO.getText());
        responseDTO.setNormalizedText(normalizeService.normalizeText(requestDTO));

        // hand the answer to the view, then render it - once for the whole flow
        normalizeView.setResponseDTO(responseDTO);
        normalizeView.display();
    }

    // Option 5: the rules working on the sample cases - once.
    public void showCases() {
        NormalizeResponseDTO responseDTO = new NormalizeResponseDTO();

        // every sample case before and after
        responseDTO.setCaseList(normalizeService.normalizeCases());

        // hand the answer to the view, then render it - once for the whole flow
        normalizeView.setResponseDTO(responseDTO);
        normalizeView.display();
    }
}
