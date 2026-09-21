package controller;

import dto.AnalysisRequestDTO;
import dto.AnalysisResponseDTO;
import service.AnalysisString;
import view.AnalysisView;

/**
 * CONTROLLER (Facade): receives the string from main, asks the service to analyse it, and
 * hands the result to the view once. No Scanner, no print, no model.
 *
 * @author HE176322
 */
public class AnalysisController {

    // Does the analysis (the brief's AnalysisString class; Controller -> Service ->
    // Repository -> Model).
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
        AnalysisResponseDTO responseDTO = analysisString.analyze(requestDTO);

        // hand the result to the view, then render it - once for the whole flow
        analysisView.setResponseDTO(responseDTO);
        analysisView.display();
    }
}
