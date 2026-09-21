package service;

import dto.MatrixRequestDTO;
import dto.MatrixResponseDTO;
import model.Matrix;
import repository.MatrixRepository;

/**
 * SERVICE and Strategy CONTEXT: keeps the two matrixes in the repository, asks the factory
 * for the chosen operation, runs it and packs the result for the view. Called only by the
 * controller; no print, no keyboard.
 *
 * @author HE176322
 */
public class MatrixService {

    // Creates the right IMatrixOperation for a menu option.
    private OperationFactory operationFactory;

    // Keeps the two matrixes typed by the user (Service -> Repository -> Model).
    private MatrixRepository matrixRepository;

    // Creates the service together with its factory and its repository.
    public MatrixService() {
        operationFactory = new OperationFactory();
        matrixRepository = new MatrixRepository();
    }

    // Runs the chosen operation on the two matrixes of the request (main has already
    // checked that their sizes fit it) and packs the result block for the view.
    public MatrixResponseDTO calculateMatrix(MatrixRequestDTO requestDTO) {
        MatrixResponseDTO responseDTO = new MatrixResponseDTO();
        IMatrixOperation operation = null;
        Matrix firstMatrix = null;
        Matrix secondMatrix = null;
        Matrix resultMatrix = null;

        // the repository turns the request into the two models and keeps them
        matrixRepository.saveMatrixes(requestDTO);
        firstMatrix = matrixRepository.getFirstMatrix();
        secondMatrix = matrixRepository.getSecondMatrix();

        // the factory picks the strategy of the option; it computes the result with the
        // brief's method
        operation = operationFactory.createOperation(requestDTO.getOperation());
        resultMatrix = new Matrix(operation.calculate(firstMatrix, secondMatrix));

        // the four parts of the result block, already as text (Matrix.toString)
        responseDTO.setFirstMatrix(firstMatrix.toString());
        responseDTO.setSymbol(operation.getSymbol());
        responseDTO.setSecondMatrix(secondMatrix.toString());
        responseDTO.setResultMatrix(resultMatrix.toString());
        return responseDTO;
    }
}
