package controller;

import dto.AnalysisRequestDTO;
import dto.AnalysisResponseDTO;
import service.AnalysisString;
import view.AnalysisView;

/**
 * CONTROLLER (Facade): receives the string from main, asks the service to analyse it, and
 * hands the result to the view.
 *
 * @author HE176322
 */
public class AnalysisController {

    // Does the analysis (the brief's AnalysisString class).
    private AnalysisString analysisString;
    // Prints the result.
    private AnalysisView analysisView;

    // Creates the controller with its service and view.
    public AnalysisController() {
        analysisString = new AnalysisString();
        analysisView = new AnalysisView();
    }

    // Function 2 workflow: analyse, then display.
    public void analyzeString(AnalysisRequestDTO requestDTO) {
        AnalysisResponseDTO response = analysisString.analyze(requestDTO);
        analysisView.setResponse(response);
        analysisView.display();
    }
}
