package controller;

import dto.CaseResponseDTO;
import dto.DocumentResponseDTO;
import dto.NormalizeRequestDTO;
import java.util.ArrayList;
import repository.DocumentRepository;
import service.EndDotRule;
import service.FirstLetterUpperRule;
import service.LowerCaseRule;
import service.NoSpaceBeforePunctuationRule;
import service.NormalizeRule;
import service.NormalizeService;
import service.OneSpaceRule;
import service.QuoteRule;
import service.RemoveBlankLineRule;
import service.SentenceCapitalRule;
import service.SpaceAfterPunctuationRule;
import view.NormalizeView;

/**
 * CONTROLLER (and Facade): receives the request from main, asks the service for the
 * result, and hands that result to the view.
 *
 * @author HE176322
 */
public class NormalizeController {

    // Normalizes and drives the file job.
    private NormalizeService normalizeService;
    // Prints every result.
    private NormalizeView normalizeView;

    // Creates the controller: text-file storage and the brief's rules.
    public NormalizeController() {
        normalizeService = new NormalizeService(new DocumentRepository(), createRules());
        normalizeView = new NormalizeView();
    }

    // The brief's rules in the order they must run.
    private ArrayList<NormalizeRule> createRules() {
        ArrayList<NormalizeRule> rules = new ArrayList<>();
        rules.add(new RemoveBlankLineRule());
        rules.add(new OneSpaceRule());
        rules.add(new LowerCaseRule());
        rules.add(new NoSpaceBeforePunctuationRule());
        rules.add(new SpaceAfterPunctuationRule());
        rules.add(new QuoteRule());
        rules.add(new FirstLetterUpperRule());
        rules.add(new SentenceCapitalRule());
        rules.add(new EndDotRule());
        return rules;
    }

    // Option 1: writes the sample input.txt.
    public void createSample() throws Exception {
        normalizeView.setDocument(normalizeService.createSample());
        normalizeView.displaySampleCreated();
    }

    // Option 2: reads input.txt, normalizes it, writes output.txt, then shows before and
    // after.
    public void normalizeFile() throws Exception {
        DocumentResponseDTO document = normalizeService.normalizeFile();
        normalizeView.setDocument(document);
        normalizeView.displayNormalized();
    }

    // Option 3: shows output.txt as it is on the disk.
    public void showOutput() throws Exception {
        normalizeView.setDocument(normalizeService.readOutput());
        normalizeView.displayOutputFile();
    }

    // Option 4: normalizes one typed line and shows it before and after.
    public void normalizeText(NormalizeRequestDTO requestDTO) {
        normalizeView.setCase(normalizeService.normalizeText(requestDTO));
        normalizeView.displayCase();
    }

    // Option 5: shows the rules working on the sample cases.
    public void showCases() {
        ArrayList<CaseResponseDTO> cases = normalizeService.normalizeCases();
        normalizeView.setCases(cases);
        normalizeView.displayCases();
    }
}
