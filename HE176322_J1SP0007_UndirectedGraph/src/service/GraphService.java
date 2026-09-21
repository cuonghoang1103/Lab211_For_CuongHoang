package service;

import constants.Constants;
import constants.Message;
import dto.GraphRequestDTO;
import dto.GraphResponseDTO;
import repository.GraphRepository;

/**
 * SERVICE: builds the brief's graph in the repository and answers "is (start, end) an
 * edge?". Called only by the controller; no print, no keyboard.
 *
 * @author HE176322
 */
public class GraphService {

    // Keeps the graph (Service -> Repository -> Model).
    private GraphRepository graphRepository;

    // Creates the service and fills the repository with the five edges of the brief's
    // figure.
    public GraphService() {
        graphRepository = new GraphRepository();

        // add each edge of the figure once; addEdge fills both cells
        for (int[] edgeArray : Constants.EDGES) {
            graphRepository.addEdge(edgeArray[0], edgeArray[1]);
        }
    }

    // Checks whether the two points of the request are joined by an edge, and answers
    // with the sentence of the brief's screen.
    public GraphResponseDTO checkEdge(GraphRequestDTO requestDTO) {
        GraphResponseDTO responseDTO = new GraphResponseDTO();

        // joined: the brief's sentence "This is  an edge"
        if (graphRepository.isEdge(requestDTO.getStart(), requestDTO.getEnd())) {
            responseDTO.setMessage(Message.IS_EDGE);
        } else {
            // not joined: the same sentence with "not"
            responseDTO.setMessage(Message.NOT_EDGE);
        }

        return responseDTO;
    }
}
