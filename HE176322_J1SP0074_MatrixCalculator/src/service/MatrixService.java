package service;

import dto.MatrixDTO;
import dto.MatrixRequestDTO;
import dto.MatrixResponseDTO;
import model.Matrix;

/**
 * SERVICE and Strategy CONTEXT: turns the request into Matrix models, asks the factory
 * for the chosen operation, runs it and packs the result for the view.
 *
 * @author HE176322
 */
public class MatrixService {

    // Creates the right MatrixOperation for a menu option.
    private OperationFactory operationFactory = new OperationFactory();

    // Creates the service.
    public MatrixService() {
    }

    // Checks the two shapes BEFORE the user types the values of matrix 2, so a wrong size
    // costs one line of typing instead of a whole matrix.
    public void checkMatrixSize(MatrixRequestDTO requestDTO) throws Exception {
        MatrixOperation operation
                = operationFactory.createOperation(requestDTO.getOperation());
        operation.checkSize(toMatrix(requestDTO.getFirstMatrix()),
                toMatrix(requestDTO.getSecondMatrix()));
    }

    // Runs the chosen operation.
    public MatrixResponseDTO calculateMatrix(MatrixRequestDTO requestDTO)
            throws Exception {
        MatrixOperation operation
                = operationFactory.createOperation(requestDTO.getOperation());
        Matrix first = toMatrix(requestDTO.getFirstMatrix());
        Matrix second = toMatrix(requestDTO.getSecondMatrix());
        operation.checkSize(first, second);
        Matrix result = new Matrix(operation.calculate(first, second));
        MatrixResponseDTO response = new MatrixResponseDTO();
        response.setFirstMatrix(first.toString());
        response.setSymbol(operation.getSymbol());
        response.setSecondMatrix(second.toString());
        response.setResultMatrix(result.toString());
        return response;
    }

    // Copies the values the user typed into the model class.
    private Matrix toMatrix(MatrixDTO matrixDTO) {
        return new Matrix(matrixDTO.getValues());
    }
}
