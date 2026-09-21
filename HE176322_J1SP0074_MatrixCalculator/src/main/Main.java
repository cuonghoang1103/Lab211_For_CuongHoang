package main;

import constants.Constants;
import constants.Message;
import controller.MatrixController;
import dto.MatrixDTO;
import dto.MatrixRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow - the menu loop and the keyboard. Every read and every validation
 * (whether the two sizes fit the operation included) happen here; each option then calls
 * the controller exactly once.
 *
 * @author HE176322
 */
public final class Main {

    // Private constructor: Main only has static methods (checklist 3.4).
    private Main() {
    }

    // Starts the program: shows the menu until the user chooses Quit.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MatrixController controller = new MatrixController();
        MatrixRequestDTO requestDTO = null;
        boolean running = true;
        int choice = 0;

        // show the menu again after every calculation, until Quit
        while (running) {
            System.out.println(Message.MENU);
            choice = inputChoice(sc);

            // a size that does not fit the operation is shown here
            try {
                // run the option the user picked
                switch (choice) {
                    // option 4: stop the loop
                    case Constants.MENU_QUIT:
                        running = false;
                        System.out.println(Message.GOODBYE);
                        break;

                    // options 1-3: the title, matrix 1, the size of matrix 2 (checked at
                    // once), its values, then ONE call calculates and displays
                    default:
                        requestDTO = createRequest(choice);
                        inputSize(sc, requestDTO.getFirstMatrix());
                        inputValues(sc, requestDTO.getFirstMatrix());
                        inputSize(sc, requestDTO.getSecondMatrix());
                        checkMatrixSize(requestDTO);
                        inputValues(sc, requestDTO.getSecondMatrix());
                        controller.calculateMatrix(requestDTO);
                        break;
                }
            } catch (Exception e) {
                // the size error, written in Message and thrown by Validation
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a menu choice until it is a number from 1 to 4.
    private static int inputChoice(Scanner sc) {
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_CHOICE);
            line = sc.nextLine();

            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.MENU_MIN,
                        Constants.MENU_QUIT);
            } catch (Exception e) {
                // not a number, or not from 1 to 4
                System.out.println(e.getMessage());
            }
        }
    }

    // Prints the title of the chosen operation and prepares the request with matrix 1 and
    // matrix 2 still empty.
    private static MatrixRequestDTO createRequest(int choice) {
        MatrixRequestDTO requestDTO = new MatrixRequestDTO();

        // the title of each option, from the brief's screen
        switch (choice) {
            // option 1
            case Constants.MENU_ADD:
                System.out.println(Message.TITLE_ADDITION);
                break;

            // option 2
            case Constants.MENU_SUBTRACT:
                System.out.println(Message.TITLE_SUBTRACTION);
                break;

            // option 3
            default:
                System.out.println(Message.TITLE_MULTIPLICATION);
                break;
        }

        // the operation, then two empty matrixes numbered 1 and 2 for the prompts
        requestDTO.setOperation(choice);
        requestDTO.setFirstMatrix(new MatrixDTO(Constants.FIRST_MATRIX));
        requestDTO.setSecondMatrix(new MatrixDTO(Constants.SECOND_MATRIX));
        return requestDTO;
    }

    // Asks for the rows and columns of one matrix and gives it an empty table of that
    // shape.
    private static void inputSize(Scanner sc, MatrixDTO matrixDTO) {
        int rows = inputNumberOfCells(sc,
                String.format(Message.INPUT_ROW, matrixDTO.getNumber()));
        int columns = inputNumberOfCells(sc,
                String.format(Message.INPUT_COLUMN, matrixDTO.getNumber()));

        // an empty table of the typed shape; inputValues fills it
        matrixDTO.setValueArray(new int[rows][columns]);
    }

    // Asks for one row count or column count until it is legal.
    private static int inputNumberOfCells(Scanner sc, String prompt) {
        String line = "";

        // keep asking until the size is legal
        while (true) {
            System.out.print(prompt);
            line = sc.nextLine();

            // a letter, 0 or 21 prints the reason and loops again
            try {
                return Validation.getSize(line);
            } catch (Exception e) {
                // show why the size was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Checks, right after the size of matrix 2 and before its values, that the two sizes
    // fit the chosen operation - a wrong size costs one line, not a whole matrix.
    private static void checkMatrixSize(MatrixRequestDTO requestDTO) throws Exception {
        int[][] firstArray = requestDTO.getFirstMatrix().getValueArray();
        int[][] secondArray = requestDTO.getSecondMatrix().getValueArray();

        // multiplication: the columns of matrix 1 must equal the rows of matrix 2
        if (requestDTO.getOperation() == Constants.MENU_MULTIPLY) {
            Validation.checkMultiplySize(firstArray, secondArray);
        } else {
            // addition, subtraction: the same number of rows and of columns
            Validation.checkSameSize(firstArray, secondArray);
        }
    }

    // Asks for every value of one matrix, row by row, as the brief's screen does: "Enter
    // Matrix1[1][1]:", "Enter Matrix1[1][2]:" ...
    private static void inputValues(Scanner sc, MatrixDTO matrixDTO) {
        int[][] valueArray = matrixDTO.getValueArray();

        // every row; the user sees rows and columns counted from 1
        for (int row = 0; row < valueArray.length; row++) {
            // every column of that row
            for (int column = 0; column < valueArray[row].length; column++) {
                valueArray[row][column] = inputValue(sc, String.format(
                        Message.INPUT_VALUE, matrixDTO.getNumber(), row + 1, column + 1));
            }
        }
    }

    // Asks for one value until it is a whole number.
    private static int inputValue(Scanner sc, String prompt) {
        String line = "";

        // keep asking the same cell until it is a number
        while (true) {
            System.out.print(prompt);
            line = sc.nextLine();

            // "a" prints "Values of matrix must be the number" and asks again
            try {
                return Validation.getValue(line);
            } catch (Exception e) {
                // the brief's message
                System.out.println(e.getMessage());
            }
        }
    }
}
