package main;

import constants.Constants;
import constants.Message;
import controller.EmployeeController;
import dto.EmployeeRequestDTO;
import dto.EmployeeResponseDTO;
import java.util.Date;
import java.util.Scanner;
import utils.FormatUtils;
import utils.Validation;

/**
 * MAIN: the work flow of the program - the menu loop and the keyboard.
 *
 * @author HE176322
 */
public class Main {

    // Starts the program: shows the menu until the user chooses Exit.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EmployeeController controller = new EmployeeController();
        boolean running = true;
        // show the menu again after every function, until Exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            int choice = inputChoice(sc);
            // any business error of the chosen function is shown here
            try {
                // run the function the user picked
                switch (choice) {
                    // option 1: add an employee
                    case Constants.MENU_ADD:
                        addEmployee(sc, controller);
                        break;
                    // option 2: update an employee
                    case Constants.MENU_UPDATE:
                        updateEmployee(sc, controller);
                        break;
                    // option 3: remove an employee
                    case Constants.MENU_REMOVE:
                        removeEmployee(sc, controller);
                        break;
                    // option 4: search employees by name
                    case Constants.MENU_SEARCH:
                        searchByName(sc, controller);
                        break;
                    // option 5: sort employees by salary
                    case Constants.MENU_SORT:
                        controller.sortBySalary();
                        break;
                    // option 6: stop the loop
                    case Constants.MENU_EXIT:
                        running = false;
                        System.out.println(Message.GOODBYE);
                        break;
                    // unreachable: inputChoice only returns 1..6
                    default:
                        break;
                }
            } catch (Exception e) {
                // the message was written in Message and thrown by the controller
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a menu choice until the user types a number from 1 to 6.
    private static int inputChoice(Scanner sc) {
        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_CHOICE);
            String line = sc.nextLine();
            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.MENU_MIN,
                        Constants.MENU_EXIT);
            } catch (Exception e) {
                // "You must input a number." or "Please choose from 1 to 6."
                System.out.println(e.getMessage());
            }
        }
    }

    // Option 1: reads a new employee and calls the controller's addEmployee once
    // (checkNewId is the Id pre-check).
    private static void addEmployee(Scanner sc, EmployeeController controller)
            throws Exception {
        System.out.println(Message.TITLE_ADD);
        String id = inputNewId(sc, controller);
        EmployeeRequestDTO dto = inputEmployee(sc, null);
        dto.setId(id);
        controller.addEmployee(dto);
    }

    // Option 2: finds the employee by Id, then asks every field again with the old value
    // in brackets; Enter keeps it.
    private static void updateEmployee(Scanner sc, EmployeeController controller)
            throws Exception {
        System.out.println(Message.TITLE_UPDATE);
        controller.checkNotEmpty();
        EmployeeRequestDTO idRequest = new EmployeeRequestDTO();
        idRequest.setId(inputId(sc));
        EmployeeResponseDTO old = controller.findEmployee(idRequest);
        System.out.println(Message.KEEP_HINT);
        EmployeeRequestDTO dto = inputEmployee(sc, old);
        dto.setId(old.getId());
        controller.updateEmployee(dto);
    }

    // Option 3: reads an Id and asks the controller to remove it.
    private static void removeEmployee(Scanner sc, EmployeeController controller)
            throws Exception {
        System.out.println(Message.TITLE_REMOVE);
        controller.checkNotEmpty();
        EmployeeRequestDTO dto = new EmployeeRequestDTO();
        dto.setId(inputId(sc));
        controller.removeEmployee(dto);
    }

    // Option 4: reads a search text and asks the controller for matches.
    private static void searchByName(Scanner sc, EmployeeController controller)
            throws Exception {
        System.out.println(Message.TITLE_SEARCH);
        controller.checkNotEmpty();
        EmployeeRequestDTO dto = new EmployeeRequestDTO();
        dto.setKeyword(inputKeyword(sc));
        controller.searchByName(dto);
    }

    // Reads the nine fields after the Id, in the brief's order.
    private static EmployeeRequestDTO inputEmployee(Scanner sc, EmployeeResponseDTO old) {
        EmployeeRequestDTO dto = new EmployeeRequestDTO();
        // Add: no old values to show or keep
        if (old == null) {
            dto.setFirstName(inputFirstName(sc, null));
            dto.setLastName(inputLastName(sc, null));
            dto.setPhone(inputPhone(sc, null));
            dto.setEmail(inputEmail(sc, null));
            dto.setAddress(inputAddress(sc, null));
            dto.setDob(inputDob(sc, null));
            dto.setSex(inputSex(sc, null));
            dto.setSalary(inputSalary(sc, null));
            dto.setAgency(inputAgency(sc, null));
        } else {
            // Update: each prompt shows, and Enter keeps, the old value
            dto.setFirstName(inputFirstName(sc, old.getFirstName()));
            dto.setLastName(inputLastName(sc, old.getLastName()));
            dto.setPhone(inputPhone(sc, old.getPhone()));
            dto.setEmail(inputEmail(sc, old.getEmail()));
            dto.setAddress(inputAddress(sc, old.getAddress()));
            dto.setDob(inputDob(sc, old.getDob()));
            dto.setSex(inputSex(sc, old.getSex()));
            dto.setSalary(inputSalary(sc, FormatUtils.formatSalary(old.getSalary())));
            dto.setAgency(inputAgency(sc, old.getAgency()));
        }
        return dto;
    }

    // The prompt of one field: the padded label, plus "[old] " on Update.
    private static String prompt(String label, String current) {
        // Add: nothing to show in brackets
        if (current == null) {
            return label;
        }
        return label + String.format(Message.CURRENT_VALUE, current);
    }

    // Asks for an Id until it is not blank.
    private static String inputId(Scanner sc) {
        // keep asking until the Id is given
        while (true) {
            System.out.print(Message.LABEL_ID);
            String line = sc.nextLine();
            // blank prints "This field is required.", then asks again
            try {
                return Validation.getField(line, null);
            } catch (Exception e) {
                // show why the Id was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for an Id until it is not blank AND not used yet (the brief: the Id must be
    // unique).
    private static String inputNewId(Scanner sc, EmployeeController controller) {
        // keep asking until the Id is free
        while (true) {
            EmployeeRequestDTO probe = new EmployeeRequestDTO();
            probe.setId(inputId(sc));
            // a taken Id prints "=> Employee id E001 already exists."
            try {
                controller.checkNewId(probe);
                return probe.getId();
            } catch (Exception e) {
                // show why the Id was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the search text until it is not blank.
    private static String inputKeyword(Scanner sc) {
        // keep asking until something is typed
        while (true) {
            System.out.print(Message.INPUT_KEYWORD);
            String line = sc.nextLine();
            // blank prints "Please type something to search for."
            try {
                return Validation.getKeyword(line);
            } catch (Exception e) {
                // show why the text was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the first name until it is legal.
    private static String inputFirstName(Scanner sc, String current) {
        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(prompt(Message.LABEL_FIRST_NAME, current));
            String line = sc.nextLine();
            // blank on Add prints "This field is required.", then asks again
            try {
                return Validation.getField(line, current);
            } catch (Exception e) {
                // show why the value was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the last name until it is legal.
    private static String inputLastName(Scanner sc, String current) {
        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(prompt(Message.LABEL_LAST_NAME, current));
            String line = sc.nextLine();
            // blank on Add prints "This field is required.", then asks again
            try {
                return Validation.getField(line, current);
            } catch (Exception e) {
                // show why the value was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the phone until it is legal.
    private static String inputPhone(Scanner sc, String current) {
        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(prompt(Message.LABEL_PHONE, current));
            String line = sc.nextLine();
            // a letter prints "Phone must contain digits only.", then asks again
            try {
                return Validation.getPhone(line, current);
            } catch (Exception e) {
                // show why the value was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the email until it is legal.
    private static String inputEmail(Scanner sc, String current) {
        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(prompt(Message.LABEL_EMAIL, current));
            String line = sc.nextLine();
            // a wrong shape prints "Email must look like name@domain.com.", then asks again
            try {
                return Validation.getEmail(line, current);
            } catch (Exception e) {
                // show why the value was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the address until it is legal.
    private static String inputAddress(Scanner sc, String current) {
        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(prompt(Message.LABEL_ADDRESS, current));
            String line = sc.nextLine();
            // blank on Add prints "This field is required.", then asks again
            try {
                return Validation.getField(line, current);
            } catch (Exception e) {
                // show why the value was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the date of birth until it is legal.
    private static Date inputDob(Scanner sc, String current) {
        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(prompt(Message.LABEL_DOB, current));
            String line = sc.nextLine();
            // a wrong date prints "DOB must be a real date in yyyy-MM-dd format.", then asks again
            try {
                return Validation.getDob(line, current);
            } catch (Exception e) {
                // show why the value was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the sex until it is legal.
    private static String inputSex(Scanner sc, String current) {
        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(prompt(Message.LABEL_SEX, current));
            String line = sc.nextLine();
            // another word prints "Sex must be Male or Female.", then asks again
            try {
                return Validation.getSex(line, current);
            } catch (Exception e) {
                // show why the value was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the salary until it is legal.
    private static double inputSalary(Scanner sc, String current) {
        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(prompt(Message.LABEL_SALARY, current));
            String line = sc.nextLine();
            // letters or a number <= 0 print the reason, then asks again
            try {
                return Validation.getSalary(line, current);
            } catch (Exception e) {
                // show why the value was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the agency until it is legal.
    private static String inputAgency(Scanner sc, String current) {
        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(prompt(Message.LABEL_AGENCY, current));
            String line = sc.nextLine();
            // blank on Add prints "This field is required.", then asks again
            try {
                return Validation.getField(line, current);
            } catch (Exception e) {
                // show why the value was refused
                System.out.println(e.getMessage());
            }
        }
    }
}
