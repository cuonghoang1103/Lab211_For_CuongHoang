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
 * MAIN: the work flow - the menu loop and the keyboard. Every keyboard read and every
 * validation happen here; each flow then calls the controller once.
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
        CandidateController controller = new CandidateController();
        CandidateRequestDTO requestDTO = null;
        CandidateType candidateType = null;
        boolean running = true;
        int choice = 0;

        // show the menu again after every function, until Exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            choice = inputChoice(sc);

            // any business error of the chosen function is shown here
            try {
                // run the function the user picked: one call to the controller per flow
                switch (choice) {
                    // option 1: create Experience candidates
                    case Constants.MENU_EXPERIENCE:
                    // option 2: create Fresher candidates
                    case Constants.MENU_FRESHER:
                    // option 3: create Intern candidates - one create screen for the three
                    // kinds: the title, then one candidate per round (one call to the
                    // controller each) until the answer to the brief's question is N
                    case Constants.MENU_INTERN:
                        candidateType = CandidateType.findByMenuChoice(choice);
                        System.out.println(String.format(Message.TITLE_CREATE,
                                candidateType.getLabel()));

                        // one candidate per round (one call to the controller each)
                        do {
                            requestDTO = inputCandidate(sc, candidateType);
                            createCandidate(controller, requestDTO);
                        } while (inputYesNo(sc)); // Y repeats, N leaves the loop

                        // N: the brief's "returns main screen and display all candidates
                        // who are created"
                        controller.displayAllCandidates();
                        break;

                    // option 4: the brief's search screen - the names are shown BEFORE
                    // the questions, so it is two flows in a row: the names (nobody yet:
                    // "The candidate list is empty."), then the search itself
                    case Constants.MENU_SEARCH:
                        controller.displayCandidateNames();
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
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_CHOICE);
            line = sc.nextLine();

            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.MENU_MIN, Constants.MENU_EXIT);
            } catch (Exception e) {
                // "You must input a number." or "Please choose from 1 to 5."
                System.out.println(e.getMessage());
            }
        }
    }

    // Reads one candidate: the seven common fields, then the fields of the chosen kind.
    private static CandidateRequestDTO inputCandidate(Scanner sc, CandidateType candidateType) {
        CandidateRequestDTO requestDTO = new CandidateRequestDTO();

        // the kind chosen in the menu, then the seven common fields (a blank id or name is
        // refused by the service: "already exists" needs the whole list anyway)
        requestDTO.setType(candidateType);
        requestDTO.setId(inputText(sc, Message.INPUT_ID));
        requestDTO.setFirstName(inputText(sc, Message.INPUT_FIRST_NAME));
        requestDTO.setLastName(inputText(sc, Message.INPUT_LAST_NAME));
        requestDTO.setBirthDate(inputBirthDate(sc));
        requestDTO.setAddress(inputText(sc, Message.INPUT_ADDRESS));
        requestDTO.setPhone(inputPhone(sc));
        requestDTO.setEmail(inputEmail(sc));

        // then the fields only this kind has
        inputExtraInfo(sc, requestDTO);
        return requestDTO;
    }

    // Reads the fields only the chosen kind has (the questions differ, so this is the one
    // place that switches on the type).
    private static void inputExtraInfo(Scanner sc, CandidateRequestDTO requestDTO) {
        // each kind has its own extra questions
        switch (requestDTO.getType()) {
            // Experience: years and skill
            case EXPERIENCE:
                requestDTO.setExpInYear(inputExperience(sc));
                requestDTO.setProSkill(inputText(sc, Message.INPUT_SKILL));
                break;

            // Fresher: graduation date, rank, education
            case FRESHER:
                requestDTO.setGraduationDate(inputText(sc, Message.INPUT_GRADUATION_DATE));
                requestDTO.setGraduationRank(inputRank(sc));
                requestDTO.setEducation(inputText(sc, Message.INPUT_EDUCATION));
                break;

            // Intern: majors, semester, university
            case INTERN:
                requestDTO.setMajors(inputText(sc, Message.INPUT_MAJORS));
                requestDTO.setSemester(inputSemester(sc));
                requestDTO.setUniversityName(inputText(sc, Message.INPUT_UNIVERSITY));
                break;

            // unreachable: the menu only creates the three kinds
            default:
                break;
        }
    }

    // Prints a prompt and returns the line typed without surrounding spaces (a blank line
    // is allowed here: the service decides whether a blank id or name is an error).
    private static String inputText(Scanner sc, String prompt) {
        System.out.print(prompt);
        return Validation.getText(sc.nextLine());
    }

    // Asks for the birth date until it is a 4-digit year 1900..current year.
    private static int inputBirthDate(Scanner sc) {
        String line = "";

        // keep asking until the year is legal
        while (true) {
            System.out.print(Message.INPUT_BIRTH_DATE);
            line = sc.nextLine();

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
        String line = "";

        // keep asking until the phone is legal
        while (true) {
            System.out.print(Message.INPUT_PHONE);
            line = sc.nextLine();

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
        String line = "";

        // keep asking until the email is legal
        while (true) {
            System.out.print(Message.INPUT_EMAIL);
            line = sc.nextLine();

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
        String line = "";

        // keep asking until the number is legal
        while (true) {
            System.out.print(Message.INPUT_EXPERIENCE);
            line = sc.nextLine();

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
        String line = "";

        // keep asking until the rank is legal
        while (true) {
            System.out.print(Message.INPUT_RANK);
            line = sc.nextLine();

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
        String line = "";

        // keep asking until the line is a number
        while (true) {
            System.out.print(Message.INPUT_SEMESTER);
            line = sc.nextLine();

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
        String line = "";

        // keep asking until the answer is Y or N
        while (true) {
            System.out.print(Message.ASK_CONTINUE);
            line = sc.nextLine();

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
        String line = "";

        // keep asking until the type is legal
        while (true) {
            System.out.print(Message.INPUT_TYPE);
            line = sc.nextLine();

            // a wrong type prints the reason and loops again
            try {
                return Validation.getCandidateType(line);
            } catch (Exception e) {
                // "You must input a number." or "Please choose from 0 to 2."
                System.out.println(e.getMessage());
            }
        }
    }

    // Options 1-3, one round: sends one candidate to the controller; a refused one (id
    // taken, blank name) is reported and the create screen goes on.
    private static void createCandidate(CandidateController controller,
            CandidateRequestDTO requestDTO) {
        // the service may refuse the candidate
        try {
            controller.createCandidate(requestDTO);
        } catch (Exception e) {
            // e.g. "Candidate id [E01] already exists."
            System.out.println(e.getMessage());
        }
    }

    // Option 4, second flow: the brief's two questions - a name (first or last), then a
    // type 0..2 - and one call to the controller to search.
    private static void searchCandidate(Scanner sc, CandidateController controller)
            throws Exception {
        CandidateRequestDTO requestDTO = new CandidateRequestDTO();

        // the name and the type typed
        requestDTO.setKeyword(inputText(sc, Message.INPUT_SEARCH_NAME));
        requestDTO.setType(inputType(sc));
        controller.searchCandidate(requestDTO);
    }
}
