package main;

import constants.CandidateType;
import constants.Constants;
import constants.GraduationRank;
import constants.Message;
import controller.CandidateController;
import dto.CandidateRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow - the menu loop and the keyboard.
 *
 * @author HE176322
 */
public class Main {

    // Starts the program: shows the menu until the user chooses Exit.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CandidateController controller = new CandidateController();
        boolean running = true;
        // show the menu again after every function, until Exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            int choice = inputChoice(sc);
            // any business error of the chosen function is shown here
            try {
                // run the function the user picked
                switch (choice) {
                    case Constants.MENU_EXPERIENCE: // option 1: Experience
                    case Constants.MENU_FRESHER: // option 2: Fresher
                    case Constants.MENU_INTERN: // option 3: Intern - one flow for all
                        CandidateType type = CandidateType.fromMenuChoice(choice);
                        System.out.println(String.format(Message.TITLE_CREATE,
                                type.getLabel()));
                        // one candidate per round, until the user answers N
                        do {
                            submitCandidate(controller, inputCandidate(sc, type));
                        } while (inputYesNo(sc)); // Y repeats, N leaves the loop
                        controller.displayAllCandidates();
                        break;
                    // option 4: list, then search
                    case Constants.MENU_SEARCH:
                        searchCandidate(sc, controller);
                        break;
                    // option 5: stop the loop
                    case Constants.MENU_EXIT:
                        running = false;
                        System.out.println(Message.GOODBYE);
                        break;
                    // unreachable: inputChoice only returns 1..5
                    default:
                        break;
                }
            } catch (Exception e) {
                // the message was written in Message and thrown below main
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a menu choice until it is a number from 1 to 5.
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
                // "You must input a number." or "Please choose from 1 to 5."
                System.out.println(e.getMessage());
            }
        }
    }

    // Reads one candidate: the seven common fields, then the fields of the chosen kind.
    private static CandidateRequestDTO inputCandidate(Scanner sc, CandidateType type) {
        CandidateRequestDTO dto = new CandidateRequestDTO();
        dto.setType(type);
        System.out.print(Message.INPUT_ID);
        dto.setId(Validation.getText(sc.nextLine()));
        System.out.print(Message.INPUT_FIRST_NAME);
        dto.setFirstName(Validation.getText(sc.nextLine()));
        System.out.print(Message.INPUT_LAST_NAME);
        dto.setLastName(Validation.getText(sc.nextLine()));
        dto.setBirthDate(inputBirthDate(sc));
        System.out.print(Message.INPUT_ADDRESS);
        dto.setAddress(Validation.getText(sc.nextLine()));
        dto.setPhone(inputPhone(sc));
        dto.setEmail(inputEmail(sc));
        inputExtraInfo(sc, dto);
        return dto;
    }

    // Reads the fields only the chosen kind has (the questions differ, so this is the one
    // place that switches on the type).
    private static void inputExtraInfo(Scanner sc, CandidateRequestDTO dto) {
        // each kind has its own extra questions
        switch (dto.getType()) {
            // Experience: years and skill
            case EXPERIENCE:
                dto.setExpInYear(inputExperience(sc));
                System.out.print(Message.INPUT_SKILL);
                dto.setProSkill(Validation.getText(sc.nextLine()));
                break;
            // Fresher: graduation date, rank, education
            case FRESHER:
                System.out.print(Message.INPUT_GRADUATION_DATE);
                dto.setGraduationDate(Validation.getText(sc.nextLine()));
                dto.setGraduationRank(inputRank(sc));
                System.out.print(Message.INPUT_EDUCATION);
                dto.setEducation(Validation.getText(sc.nextLine()));
                break;
            // Intern: majors, semester, university
            case INTERN:
                System.out.print(Message.INPUT_MAJORS);
                dto.setMajors(Validation.getText(sc.nextLine()));
                dto.setSemester(inputSemester(sc));
                System.out.print(Message.INPUT_UNIVERSITY);
                dto.setUniversityName(Validation.getText(sc.nextLine()));
                break;
            // unreachable: the menu only creates the three kinds
            default:
                break;
        }
    }

    // Asks for the birth date until it is a 4-digit year 1900..current year.
    private static int inputBirthDate(Scanner sc) {
        // keep asking until the year is legal
        while (true) {
            System.out.print(Message.INPUT_BIRTH_DATE);
            String line = sc.nextLine();
            // a wrong year prints the rule and loops again
            try {
                return Validation.checkBirthDate(line);
            } catch (Exception e) {
                // show why the year was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the phone until it has at least 10 digits.
    private static String inputPhone(Scanner sc) {
        // keep asking until the phone is legal
        while (true) {
            System.out.print(Message.INPUT_PHONE);
            String line = sc.nextLine();
            // a wrong phone prints the rule and loops again
            try {
                return Validation.checkPhone(line);
            } catch (Exception e) {
                // show why the phone was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the email until it has the form account@domain.
    private static String inputEmail(Scanner sc) {
        // keep asking until the email is legal
        while (true) {
            System.out.print(Message.INPUT_EMAIL);
            String line = sc.nextLine();
            // a wrong email prints the rule and loops again
            try {
                return Validation.checkEmail(line);
            } catch (Exception e) {
                // show why the email was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the years of experience until they are 0..100.
    private static int inputExperience(Scanner sc) {
        // keep asking until the number is legal
        while (true) {
            System.out.print(Message.INPUT_EXPERIENCE);
            String line = sc.nextLine();
            // a wrong number prints the rule and loops again
            try {
                return Validation.checkExperience(line);
            } catch (Exception e) {
                // show why the number was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the rank until it is one of the four values.
    private static GraduationRank inputRank(Scanner sc) {
        // keep asking until the rank is legal
        while (true) {
            System.out.print(Message.INPUT_RANK);
            String line = sc.nextLine();
            // a wrong rank prints the four values and loops again
            try {
                return Validation.checkRank(line);
            } catch (Exception e) {
                // show why the rank was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the semester until it is a whole number.
    private static int inputSemester(Scanner sc) {
        // keep asking until the line is a number
        while (true) {
            System.out.print(Message.INPUT_SEMESTER);
            String line = sc.nextLine();
            // letters print "You must input a number." and loop again
            try {
                return Validation.getInt(line);
            } catch (Exception e) {
                // show why the semester was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks "Do you want to continue (Y/N)?" until the answer is Y or N.
    private static boolean inputYesNo(Scanner sc) {
        // keep asking until the answer is Y or N
        while (true) {
            System.out.print(Message.ASK_CONTINUE);
            String line = sc.nextLine();
            // anything else prints "Please enter Y or N." and loops again
            try {
                return Validation.checkYesNo(line);
            } catch (Exception e) {
                // show why the answer was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the search type until it is 0, 1 or 2.
    private static CandidateType inputType(Scanner sc) {
        // keep asking until the type is legal
        while (true) {
            System.out.print(Message.INPUT_TYPE);
            String line = sc.nextLine();
            // a wrong type prints the reason and loops again
            try {
                return Validation.getCandidateType(line);
            } catch (Exception e) {
                // "You must input a number." or "Please choose from 0 to 2."
                System.out.println(e.getMessage());
            }
        }
    }

    // Sends one candidate to the controller.
    private static void submitCandidate(CandidateController controller,
            CandidateRequestDTO dto) {
        // the service may refuse the candidate
        try {
            controller.createCandidate(dto);
        } catch (Exception e) {
            // e.g. "Candidate id [E01] already exists."
            System.out.println(e.getMessage());
        }
    }

    // Option 4: shows every name (pre-check: stops at once when the list is empty), then
    // reads the name and the type and asks for the search.
    private static void searchCandidate(Scanner sc, CandidateController controller)
            throws Exception {
        controller.displayCandidateNames();
        CandidateRequestDTO dto = new CandidateRequestDTO();
        System.out.print(Message.INPUT_SEARCH_NAME);
        dto.setKeyword(Validation.getText(sc.nextLine()));
        dto.setType(inputType(sc));
        controller.searchCandidate(dto);
    }
}
