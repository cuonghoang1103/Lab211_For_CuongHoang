package main;

import constants.Constants;
import constants.Message;
import controller.NoteController;
import dto.NoteRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow of the program - the menu loop and the keyboard. Every keyboard read
 * and every validation happen here; each menu option then calls the controller once.
 *
 * @author HE176322
 */
public final class Main {

    // Private constructor: Main only has static methods (checklist 3.4).
    private Main() {
    }

    // Starts the program: shows the menu until the user chooses exit.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        NoteController controller = new NoteController();
        NoteRequestDTO requestDTO = null;
        boolean running = true;
        int choice = 0;

        // show the menu again after every function, until exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            choice = inputChoice(sc);

            // a wrong value or an unknown ID stops the function; its message is shown here
            try {
                // run the function the user picked: one call to the controller per option
                switch (choice) {
                    // option 1: read and check a new note, then add it
                    case Constants.MENU_ADD:
                        requestDTO = inputNote(sc);
                        controller.addNote(requestDTO);
                        break;

                    // option 2: read and check an ID, then delete that note
                    case Constants.MENU_DELETE:
                        requestDTO = inputDelete(sc);
                        controller.deleteNote(requestDTO);
                        break;

                    // option 3: show the notes
                    case Constants.MENU_DISPLAY:
                        controller.displayNotes();
                        break;

                    // option 4: stop the loop
                    case Constants.MENU_EXIT:
                        running = false;
                        System.out.println(Message.GOODBYE);
                        break;

                    // unreachable: inputChoice only returns 1..4
                    default:
                        break;
                }
            } catch (Exception e) {
                // the message was written in Message and thrown by Validation or the repository
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a menu choice until the user types a number from 1 to 4.
    private static int inputChoice(Scanner sc) {
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_CHOICE);
            line = sc.nextLine();

            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.MENU_MIN, Constants.MENU_EXIT);
            } catch (Exception e) {
                // "You must input a number." or "Please choose from 1 to 4."
                System.out.println(e.getMessage());
            }
        }
    }

    // Prints a prompt and returns the line typed after it, not checked yet.
    private static String inputLine(Scanner sc, String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

    // Option 1: the title, then the content - checked not to be blank - into a new request.
    private static NoteRequestDTO inputNote(Scanner sc) throws Exception {
        NoteRequestDTO requestDTO = new NoteRequestDTO();
        String content = "";

        // the title of the add screen, then the content as typed
        System.out.println(Message.TITLE_ADD);
        content = inputLine(sc, Message.INPUT_CONTENT);

        // blank: the flow stops with "Content cannot be empty."
        requestDTO.setContent(Validation.getRequired(content, Message.CONTENT_EMPTY));
        return requestDTO;
    }

    // Option 2: the title, then the ID to delete - checked to be a whole number - into a
    // new request.
    private static NoteRequestDTO inputDelete(Scanner sc) throws Exception {
        NoteRequestDTO requestDTO = new NoteRequestDTO();
        String id = "";

        // the title of the delete screen, then the ID as typed
        System.out.println(Message.TITLE_DELETE);
        id = inputLine(sc, Message.INPUT_ID);

        // blank or not a number: the flow stops with "ID cannot be empty." / "ID must be..."
        requestDTO.setId(Validation.getId(id));
        return requestDTO;
    }
}
