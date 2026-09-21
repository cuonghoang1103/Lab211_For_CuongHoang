package main;

import constants.Message;
import controller.PathController;
import dto.PathRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow - read and check the path (Function 1), then call the controller
 * once (Function 2).
 *
 * @author HE176322
 */
public final class Main {

    // Private constructor: Main only has static methods (checklist 3.4).
    private Main() {
    }

    // Starts the program.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PathController controller = new PathController();
        PathRequestDTO requestDTO = new PathRequestDTO();

        // Function 1: the title, then the path typed by the user, carried by the request
        System.out.println(Message.TITLE);
        requestDTO.setFullPath(inputPath(sc));

        // Function 2: analyse and display - the controller is called once
        controller.analyzePath(requestDTO);
    }

    // Asks for the path until Validation accepts it.
    private static String inputPath(Scanner sc) {
        String line = "";

        // keep asking until the path is a legal file path
        while (true) {
            System.out.println(Message.INPUT_PATH);
            line = sc.nextLine();

            // a wrong line prints the reason and loops again
            try {
                return Validation.getFilePath(line);
            } catch (Exception e) {
                // "Path must not be empty." or the format message
                System.out.println(e.getMessage());
            }
        }
    }
}
