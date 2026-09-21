package main;

import constants.Constants;
import constants.Message;
import controller.EmployeeController;
import dto.EmployeeRequestDTO;
import java.util.Date;
import java.util.Scanner;
import utils.FormatUtils;
import utils.Validation;

/**
 * MAIN: the work flow of the program - the menu loop and the keyboard. Every keyboard read
 * and every validation happen here (each value is checked as it is entered, as the brief
 * asks); each menu option then calls the controller once.
 *
 * @author HE176322
 */
public final class Main {

    // Private constructor: Main only has static methods (checklist 3.4).
    private Main() {
    }

    // Starts the program: shows the menu until the user chooses Exit.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EmployeeController controller = new EmployeeController();
        EmployeeRequestDTO requestDTO = null;
        boolean running = true;
        int choice = 0;

        // show the menu again after every function, until Exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            choice = inputChoice(sc);

            // a business error of the chosen function is shown here
            try {
                // run the function the user picked: one call to the controller per option
                switch (choice) {
                    // option 1: the Id (checked at once) and the nine other values, then add
                    case Constants.MENU_ADD:
                        requestDTO = inputAdd(sc, controller);
                        controller.addEmployee(requestDTO);
                        break;

                    // option 2: the Id of a stored employee and its nine values, then update
                    case Constants.MENU_UPDATE:
                        requestDTO = inputUpdate(sc, controller);
                        controller.updateEmployee(requestDTO);
                        break;

                    // option 3: the Id, then remove that employee
                    case Constants.MENU_REMOVE:
                        requestDTO = inputRemove(sc, controller);
                        controller.removeEmployee(requestDTO);
                        break;

                    // option 4: the search text, then the table of the matching employees
                    case Constants.MENU_SEARCH:
                        requestDTO = inputSearch(sc, controller);
                        controller.searchByName(requestDTO);
                        break;

                    // option 5: nothing to type - the list sorted by salary
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
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_CHOICE);
            line = sc.nextLine();

            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.MENU_MIN, Constants.MENU_EXIT);
            } catch (Exception e) {
                // "You must input a number." or "Please choose from 1 to 6."
                System.out.println(e.getMessage());
            }
        }
    }

    // Option 1: the title, the Id (asked again at once while it is blank or already used),
    // then the nine other values - every field required.
    private static EmployeeRequestDTO inputAdd(Scanner sc, EmployeeController controller) {
        EmployeeRequestDTO requestDTO = null;
        String id = "";

        // the title, then the Id before any other question
        System.out.println(Message.TITLE_ADD);
        id = inputNewId(sc, controller);

        // Add: no current values to show or keep
        requestDTO = inputEmployee(sc, null);
        requestDTO.setId(id);
        return requestDTO;
    }

    // Option 2: the title, then - when the list is not empty - the Id of a stored employee
    // and its nine values, each prompt showing the current value in brackets (Enter keeps
    // it).
    private static EmployeeRequestDTO inputUpdate(Scanner sc, EmployeeController controller)
            throws Exception {
        EmployeeRequestDTO currentDTO = null;
        EmployeeRequestDTO requestDTO = null;

        // the title, then "=> The employee list is empty." at once (a check only)
        System.out.println(Message.TITLE_UPDATE);
        controller.checkNotEmpty();

        // the Id must exist; its current values come back to be shown in brackets
        currentDTO = inputCurrent(sc, controller);
        System.out.println(Message.KEEP_HINT);
        requestDTO = inputEmployee(sc, currentDTO);
        requestDTO.setId(currentDTO.getId());
        return requestDTO;
    }

    // Update: reads the Id and lets the controller load that employee's current values (a
    // check only: "=> No employee found with id E009." when nobody has it).
    private static EmployeeRequestDTO inputCurrent(Scanner sc, EmployeeController controller)
            throws Exception {
        EmployeeRequestDTO currentDTO = new EmployeeRequestDTO();

        // the Id first; the controller fills in the stored values
        currentDTO.setId(inputId(sc));
        controller.loadEmployee(currentDTO);
        return currentDTO;
    }

    // Option 3: the title, then - when the list is not empty - the Id to remove.
    private static EmployeeRequestDTO inputRemove(Scanner sc, EmployeeController controller)
            throws Exception {
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO();

        // the title, then "=> The employee list is empty." at once (a check only)
        System.out.println(Message.TITLE_REMOVE);
        controller.checkNotEmpty();

        // an unknown Id is reported by the controller's removeEmployee
        requestDTO.setId(inputId(sc));
        return requestDTO;
    }

    // Option 4: the title, then - when the list is not empty - the text to search for.
    private static EmployeeRequestDTO inputSearch(Scanner sc, EmployeeController controller)
            throws Exception {
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO();

        // the title, then "=> The employee list is empty." at once (a check only)
        System.out.println(Message.TITLE_SEARCH);
        controller.checkNotEmpty();

        // any part of a first or last name
        requestDTO.setKeyword(inputKeyword(sc));
        return requestDTO;
    }

    // The nine values after the Id, in the brief's order. Add passes no current values
    // (every field required); Update passes the stored ones (shown in brackets, Enter keeps
    // them).
    private static EmployeeRequestDTO inputEmployee(Scanner sc, EmployeeRequestDTO currentDTO) {
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO();

        // Add: no current value to show or keep
        if (currentDTO == null) {
            requestDTO.setFirstName(inputFirstName(sc, null));
            requestDTO.setLastName(inputLastName(sc, null));
            requestDTO.setPhone(inputPhone(sc, null));
            requestDTO.setEmail(inputEmail(sc, null));
            requestDTO.setAddress(inputAddress(sc, null));
            requestDTO.setDob(inputDob(sc, null));
            requestDTO.setSex(inputSex(sc, null));
            requestDTO.setSalary(inputSalary(sc, null));
            requestDTO.setAgency(inputAgency(sc, null));
        } else {
            // Update: each prompt shows, and Enter keeps, the current value (as text)
            requestDTO.setFirstName(inputFirstName(sc, currentDTO.getFirstName()));
            requestDTO.setLastName(inputLastName(sc, currentDTO.getLastName()));
            requestDTO.setPhone(inputPhone(sc, currentDTO.getPhone()));
            requestDTO.setEmail(inputEmail(sc, currentDTO.getEmail()));
            requestDTO.setAddress(inputAddress(sc, currentDTO.getAddress()));
            requestDTO.setDob(inputDob(sc, FormatUtils.formatDate(currentDTO.getDob())));
            requestDTO.setSex(inputSex(sc, currentDTO.getSex()));
            requestDTO.setSalary(inputSalary(sc,
                    FormatUtils.formatSalary(currentDTO.getSalary())));
            requestDTO.setAgency(inputAgency(sc, currentDTO.getAgency()));
        }

        return requestDTO;
    }

    // The prompt of one field: the padded label, plus "[current] " on Update.
    private static String prompt(String label, String current) {
        // Add: nothing to show in brackets
        if (current == null) {
            return label;
        }

        return String.format(Message.CURRENT_VALUE, label, current);
    }

    // Asks for an Id until it is not blank.
    private static String inputId(Scanner sc) {
        String line = "";

        // keep asking until the Id is given
        while (true) {
            System.out.print(Message.LABEL_ID);
            line = sc.nextLine();

            // blank prints "This field is required.", then asks again
            try {
                return Validation.getField(line, null);
            } catch (Exception e) {
                // show why the Id was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Add: asks for an Id until it is not blank AND not used yet - the brief: "make sure the
    // Id is not already used", checked as it is entered (a check only, nothing rendered).
    private static String inputNewId(Scanner sc, EmployeeController controller) {
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO();

        // keep asking until the Id is free
        while (true) {
            requestDTO.setId(inputId(sc));

            // a taken Id prints "=> Employee id E001 already exists.", then asks again
            try {
                controller.checkNewId(requestDTO);
                return requestDTO.getId();
            } catch (Exception e) {
                // show why the Id was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the search text until it is not blank.
    private static String inputKeyword(Scanner sc) {
        String line = "";

        // keep asking until something is typed
        while (true) {
            System.out.print(Message.INPUT_KEYWORD);
            line = sc.nextLine();

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
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(prompt(Message.LABEL_FIRST_NAME, current));
            line = sc.nextLine();

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
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(prompt(Message.LABEL_LAST_NAME, current));
            line = sc.nextLine();

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
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(prompt(Message.LABEL_PHONE, current));
            line = sc.nextLine();

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
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(prompt(Message.LABEL_EMAIL, current));
            line = sc.nextLine();

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
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(prompt(Message.LABEL_ADDRESS, current));
            line = sc.nextLine();

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
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(prompt(Message.LABEL_DOB, current));
            line = sc.nextLine();

            // a wrong date prints "DOB must be a real date in yyyy-MM-dd format."
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
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(prompt(Message.LABEL_SEX, current));
            line = sc.nextLine();

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
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(prompt(Message.LABEL_SALARY, current));
            line = sc.nextLine();

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
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(prompt(Message.LABEL_AGENCY, current));
            line = sc.nextLine();

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
