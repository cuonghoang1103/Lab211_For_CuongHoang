package main;

import constants.Constants;
import constants.Message;
import controller.MatrixController;
import dto.MatrixDTO;
import dto.MatrixRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow - the menu loop and the keyboard.
 *
 * @author HE176322
 */
public class Main {

    // Starts the program: shows the menu until the user chooses Quit.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MatrixController controller = new MatrixController();
        boolean running = true;
        // show the menu again after every calculation, until Quit
        while (running) {
            System.out.println(Message.MENU);
            int choice = inputChoice(sc);
            // a size that does not fit the operation is shown here
            try {
                // run the option the user picked
                switch (choice) {
                    // option 4: stop the loop
                    case Constants.MENU_QUIT:
                        running = false;
                        System.out.println(Message.GOODBYE);
                        break;
                    // options 1-3: addition, subtraction, multiplication
                    default:
                        MatrixRequestDTO dto = createRequest(choice);
                        inputSize(sc, dto.getFirstMatrix());
                        inputValues(sc, dto.getFirstMatrix());
                        inputSize(sc, dto.getSecondMatrix());
                        // pre-check: refuse the sizes before matrix 2's values
                        controller.checkMatrixSize(dto);
                        inputValues(sc, dto.getSecondMatrix());
                        controller.calculateMatrix(dto);
                        break;
                }
            } catch (Exception e) {
                // the message was written in Message and thrown by the service
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a menu choice until it is a number from 1 to 4.
    private static int inputChoice(Scanner sc) {
        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_CHOICE);
            String line = sc.nextLine();
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
        MatrixRequestDTO dto = new MatrixRequestDTO();
        dto.setOperation(choice);
        dto.setFirstMatrix(new MatrixDTO(Constants.FIRST_MATRIX));
        dto.setSecondMatrix(new MatrixDTO(Constants.SECOND_MATRIX));
        return dto;
    }

    // Asks for the rows and columns of one matrix and gives it an empty table of that
    // shape.
    private static void inputSize(Scanner sc, MatrixDTO matrix) {
        int rows = inputNumberOfCells(sc,
                String.format(Message.INPUT_ROW, matrix.getNumber()));
        int columns = inputNumberOfCells(sc,
                String.format(Message.INPUT_COLUMN, matrix.getNumber()));
        matrix.setValues(new int[rows][columns]);
    }

    // Asks for one row count or column count until it is legal.
    private static int inputNumberOfCells(Scanner sc, String prompt) {
        // keep asking until the size is legal
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine();
            // a letter, 0 or 21 prints the reason and loops again
            try {
                return Validation.getSize(line);
            } catch (Exception e) {
                // show why the size was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for every value of one matrix, row by row, as the brief's screen does: "Enter
    // Matrix1[1][1]:", "Enter Matrix1[1][2]:" ...
    private static void inputValues(Scanner sc, MatrixDTO matrix) {
        int[][] values = matrix.getValues();
        // every row; the user sees rows and columns counted from 1
        for (int row = 0; row < values.length; row++) {
            // every column of that row
            for (int column = 0; column < values[row].length; column++) {
                values[row][column] = inputValue(sc, String.format(
                        Message.INPUT_VALUE, matrix.getNumber(), row + 1, column + 1));
            }
        }
    }

    // Asks for one value until it is a whole number.
    private static int inputValue(Scanner sc, String prompt) {
        // keep asking the same cell until it is a number
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine();
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
