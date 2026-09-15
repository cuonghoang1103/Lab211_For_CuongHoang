package controller;

import constants.Message;
import dto.CandidateRequestDTO;
import repository.CandidateRepository;
import service.CandidateFactory;
import service.CandidateSearchService;
import service.CandidateService;
import service.NameSearchStrategy;
import view.CandidateView;

/**
 * CONTROLLER (and FACADE): receives a request DTO from main, asks a service for the
 * result and hands it to the view.
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

    // Wires the program: one repository, two services, one view.
    public CandidateController() {
        CandidateRepository repository = new CandidateRepository();
        candidateService = new CandidateService(repository, new CandidateFactory());
        searchService = new CandidateSearchService(repository, new NameSearchStrategy());
        candidateView = new CandidateView();
    }

    // Options 1-3: stores one candidate and reports it.
    public void createCandidate(CandidateRequestDTO requestDTO) throws Exception {
        candidateService.createCandidate(requestDTO);
        candidateView.showMessage(String.format(Message.CREATE_SUCCESS,
                requestDTO.getType().getLabel(), requestDTO.getId()));
    }

    // After the user answers N: every candidate with all its columns, grouped by kind
    // (the brief: "display all candidates who are created").
    public void displayAllCandidates() {
        candidateView.setCandidateMap(searchService.getCandidatesByType());
        candidateView.displayDetails();
    }

    // Option 4, first step: the names grouped by kind, shown BEFORE the search questions
    // (the brief's screen).
    public void displayCandidateNames() throws Exception {
        searchService.checkNotEmpty();
        candidateView.setCandidateMap(searchService.getCandidatesByType());
        candidateView.displayNames();
    }

    // Option 4, second step: the search itself.
    public void searchCandidate(CandidateRequestDTO requestDTO) throws Exception {
        candidateView.setFoundList(searchService.searchCandidate(requestDTO));
        candidateView.displayFound();
    }
}
