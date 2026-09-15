package main;

import constants.Message;
import controller.PathController;
import dto.PathRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow - read the path (Function 1), then call the controller once
 * (Function 2).
 *
 * @author HE176322
 */
public class Main {

    // Starts the program.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PathController controller = new PathController();
        System.out.println(Message.TITLE);
        PathRequestDTO dto = new PathRequestDTO();
        dto.setFullPath(inputPath(sc));
        controller.analyzePath(dto);
    }

    // Asks for the path until Validation accepts it.
    private static String inputPath(Scanner sc) {
        // keep asking until the path is a legal file path
        while (true) {
            System.out.println(Message.INPUT_PATH);
            String line = sc.nextLine();
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
