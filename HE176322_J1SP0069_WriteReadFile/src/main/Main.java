package main;

import constants.Message;
import controller.FileController;
import dto.FileRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow - ask to write, then ask to read.
 *
 * @author HE176322
 */
public class Main {

    // Starts the program: Function 1 (write) then Function 2 (read), each only when the
    // user answers Y.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        FileController controller = new FileController();
        System.out.println(Message.TITLE);
        // the brief: answering N skips writing but still asks about reading
        if (inputYesNo(sc, Message.ASK_WRITE)) {
            // a failed write is reported, then the program goes on to reading
            try {
                writeFile(sc, controller);
            } catch (Exception e) {
                // "Could not write the file."
                System.out.println(e.getMessage());
            }
        }
        // the brief: answering N here ends the program
        if (inputYesNo(sc, Message.ASK_READ)) {
            // a missing file is reported instead of crashing
            try {
                readFile(sc, controller);
            } catch (Exception e) {
                // "File does not exist." or "Could not read the file."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks a Y/N question until the answer is Y, N, y or n.
    private static boolean inputYesNo(Scanner sc, String question) {
        // keep asking until Validation accepts the answer
        while (true) {
            System.out.print(question);
            String line = sc.nextLine();
            // a wrong answer prints "Please answer Y or N." and loops again
            try {
                return Validation.getYesNo(line);
            } catch (Exception e) {
                // show why the answer was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a file path until it is not blank.
    private static String inputPath(Scanner sc) {
        // keep asking until the path is not blank
        while (true) {
            System.out.print(Message.INPUT_PATH);
            String line = sc.nextLine();
            // a blank path prints "Path must not be empty." and loops again
            try {
                return Validation.getNonBlank(line, Message.PATH_EMPTY);
            } catch (Exception e) {
                // show why the path was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Reads content lines until the user types save or SAVE, and joins them with the line
    // separator of the machine.
    private static String inputContent(Scanner sc) {
        StringBuilder content = new StringBuilder();
        boolean firstLine = true;
        String line = sc.nextLine();
        // collect lines until the stop word is typed
        while (!Validation.isSaveCommand(line)) {
            // a line break goes BETWEEN lines, not before the first one
            if (!firstLine) {
                content.append(System.lineSeparator());
            }
            content.append(line);
            firstLine = false;
            line = sc.nextLine();
        }
        return content.toString();
    }

    // Function 1: reads the path and the content, then calls the controller once.
    private static void writeFile(Scanner sc, FileController controller)
            throws Exception {
        FileRequestDTO dto = new FileRequestDTO();
        dto.setPath(inputPath(sc));
        System.out.println(Message.SAVE_HINT);
        System.out.println(Message.INPUT_CONTENT);
        dto.setContent(inputContent(sc));
        controller.writeFile(dto);
    }

    // Function 2: reads the path, then calls the controller once.
    private static void readFile(Scanner sc, FileController controller)
            throws Exception {
        FileRequestDTO dto = new FileRequestDTO();
        dto.setPath(inputPath(sc));
        controller.readFile(dto);
    }
}
