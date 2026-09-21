package main;

import constants.Constants;
import constants.Message;
import controller.PersonController;
import dto.PersonDTO;
import dto.PersonRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow - read and check three persons (Function 1), then call the
 * controller once to sort and display them (Functions 2 and 3).
 *
 * @author HE176322
 */
public final class Main {

    // Private constructor: Main only has static methods (checklist 3.4).
    private Main() {
    }

    // Starts the program: reads the three persons, then calls the controller once.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PersonController controller = new PersonController();
        PersonRequestDTO requestDTO = new PersonRequestDTO();
        PersonDTO[] personArray = new PersonDTO[Constants.NUMBER_OF_PERSONS];

        // Function 1: the title of the brief's screen, once
        System.out.println(Message.TITLE);

        // the brief: an array of 3 persons, filled one after another
        for (int i = 0; i < personArray.length; i++) {
            personArray[i] = inputPerson(sc);
        }

        // Function 2 (auto next): the three persons go to the controller in the request
        requestDTO.setPersonArray(personArray);

        // sort and display - the controller is called once; a business error of the
        // service is shown instead of a crash
        try {
            controller.displaySortedPersons(requestDTO);
        } catch (Exception e) {
            // "Salary is greater than zero" or "Can't Sort Person"
            System.out.println(e.getMessage());
        }
    }

    // Reads the name, address and salary of one person.
    private static PersonDTO inputPerson(Scanner sc) {
        PersonDTO personDTO = new PersonDTO();

        // the brief's line before each person, then its three fields
        System.out.println(Message.TITLE_INPUT);
        personDTO.setName(inputName(sc));
        personDTO.setAddress(inputAddress(sc));
        personDTO.setSalary(inputSalary(sc));
        return personDTO;
    }

    // Asks for the name until it is not blank.
    private static String inputName(Scanner sc) {
        String line = "";

        // keep asking until the name is not blank
        while (true) {
            System.out.print(Message.INPUT_NAME);
            line = sc.nextLine();

            // a blank line prints "You must input name." and loops again
            try {
                return Validation.getNonBlank(line, Message.NAME_EMPTY);
            } catch (Exception e) {
                // show why the name was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the address until it is not blank.
    private static String inputAddress(Scanner sc) {
        String line = "";

        // keep asking until the address is not blank
        while (true) {
            System.out.print(Message.INPUT_ADDRESS);
            line = sc.nextLine();

            // a blank line prints "You must input address." and loops again
            try {
                return Validation.getNonBlank(line, Message.ADDRESS_EMPTY);
            } catch (Exception e) {
                // show why the address was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the salary until it is a number greater than zero.
    private static double inputSalary(Scanner sc) {
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_SALARY);
            line = sc.nextLine();

            // a wrong line prints one of the brief's three messages
            try {
                return Validation.checkSalary(line);
            } catch (Exception e) {
                // "You must input Salary.", "You must input digit." or
                // "Salary is greater than zero"
                System.out.println(e.getMessage());
            }
        }
    }
}
