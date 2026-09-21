package controller;

import dto.CandidateRequestDTO;
import dto.CandidateResponseDTO;
import repository.CandidateRepository;
import repository.ICandidateRepository;
import service.CandidateFactory;
import service.CandidateSearchService;
import service.CandidateService;
import service.NameSearchStrategy;
import view.CandidateView;

/**
 * CONTROLLER (and FACADE): receives a request DTO from main, asks a service for the
 * answer and hands it to the view - one render per flow. No Scanner, no print, no model;
 * a broken rule is thrown as an Exception(Message.X) for main to print.
 *
 * @author HE176322
 */
public class CandidateController {

    // Create workflow.
    private CandidateService candidateService;

    // List and search workflows.
    private CandidateSearchService searchService;

    // Prints every result.
    private CandidateView candidateView;

    // Wires the program - the one place that names the concrete classes: ONE repository,
    // held through its interface, seen by each service only through the half it needs.
    public CandidateController() {
        ICandidateRepository candidateRepository = new CandidateRepository();

        candidateService = new CandidateService(candidateRepository, new CandidateFactory());
        searchService = new CandidateSearchService(candidateRepository,
                new NameSearchStrategy());
        candidateView = new CandidateView();
    }

    // Options 1-3, one candidate: the service stores it (a blank or taken id, a blank
    // name is thrown), then the view prints "... candidate [id] has been created." - once.
    public void createCandidate(CandidateRequestDTO requestDTO) throws Exception {
        CandidateResponseDTO responseDTO = candidateService.createCandidate(requestDTO);

        // one render for the whole flow
        candidateView.setResponseDTO(responseDTO);
        candidateView.display();
    }

    // Options 1-3, the answer N: every candidate with all its columns, grouped by kind
    // (the brief: "display all candidates who are created") - once.
    public void displayAllCandidates() {
        CandidateResponseDTO responseDTO = searchService.getAllCandidates();

        // one render for the whole flow
        candidateView.setResponseDTO(responseDTO);
        candidateView.display();
    }

    // Option 4, first flow: the names grouped by kind, shown BEFORE the search questions
    // (the brief's screen) - once; nobody yet is thrown.
    public void displayCandidateNames() throws Exception {
        CandidateResponseDTO responseDTO = searchService.getCandidateNames();

        // one render for the whole flow
        candidateView.setResponseDTO(responseDTO);
        candidateView.display();
    }

    // Option 4, second flow: the search itself - once; a blank name or no match is
    // thrown.
    public void searchCandidate(CandidateRequestDTO requestDTO) throws Exception {
        CandidateResponseDTO responseDTO = searchService.searchCandidate(requestDTO);

        // one render for the whole flow
        candidateView.setResponseDTO(responseDTO);
        candidateView.display();
    }
}
