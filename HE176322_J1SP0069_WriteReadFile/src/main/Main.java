package main;

import constants.Message;
import controller.FileController;
import dto.FileRequestDTO;
import java.util.Scanner;
import utils.FileUtils;
import utils.Validation;

/**
 * MAIN: the work flow - ask to write, then ask to read. Every keyboard read, every
 * validation and the reading of the file happen here; each flow then calls the controller
 * exactly once.
 *
 * @author HE176322
 */
public final class Main {

    // Private constructor: Main only has static methods (checklist 3.4).
    private Main() {
    }

    // Starts the program: Function 1 (write) then Function 2 (read), each only when the
    // user answers Y.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        FileController controller = new FileController();

        // the title of the brief's screen, once
        System.out.println(Message.TITLE);

        // the brief: answering N skips writing but still asks about reading
        if (inputYesNo(sc, Message.ASK_WRITE)) {
            // Function 1: one call of the controller; a failed write is reported, then
            // the program goes on to reading
            try {
                controller.writeFile(inputWriteFile(sc));
            } catch (Exception e) {
                // "Could not write the file."
                System.out.println(e.getMessage());
            }
        }

        // the brief: answering N here ends the program
        if (inputYesNo(sc, Message.ASK_READ)) {
            // Function 2: main reads the file, then one call of the controller shows it;
            // a missing file is reported instead of crashing
            try {
                controller.displayFile(inputReadFile(sc));
            } catch (Exception e) {
                // "File does not exist." or "Could not read the file."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks a Y/N question until the answer is Y, N, y or n.
    private static boolean inputYesNo(Scanner sc, String question) {
        String line = "";

        // keep asking until Validation accepts the answer
        while (true) {
            System.out.print(question);
            line = sc.nextLine();

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
        String line = "";

        // keep asking until the path is not blank
        while (true) {
            System.out.print(Message.INPUT_PATH);
            line = sc.nextLine();

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

            // keep the line, then read the next one
            content.append(line);
            firstLine = false;
            line = sc.nextLine();
        }

        return content.toString();
    }

    // Function 1: asks for the path and the content of the file to write.
    private static FileRequestDTO inputWriteFile(Scanner sc) {
        FileRequestDTO requestDTO = new FileRequestDTO();

        // the path first, then the brief's two lines, then the content
        requestDTO.setPath(inputPath(sc));
        System.out.println(Message.SAVE_HINT);
        System.out.println(Message.INPUT_CONTENT);
        requestDTO.setContent(inputContent(sc));
        return requestDTO;
    }

    // Function 2: asks for the path, then reads the file into the request with the
    // brief's readFile (checklist 1.1: main reads the files).
    private static FileRequestDTO inputReadFile(Scanner sc) throws Exception {
        FileRequestDTO requestDTO = new FileRequestDTO();

        // the path of the file to read
        requestDTO.setPath(inputPath(sc));

        // FileUtils refuses a missing file or a folder, else gives its whole content
        requestDTO.setContent(FileUtils.readFile(requestDTO.getPath()));
        return requestDTO;
    }
}
