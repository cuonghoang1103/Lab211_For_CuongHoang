package main;

import constants.Car;
import constants.Color;
import constants.Day;
import constants.Message;
import controller.ShowroomController;
import dto.CarRequestDTO;
import exceptions.CarException;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow - read one request, check it, ask "find more?". Every keyboard read
 * happens here; each request then calls the controller once.
 *
 * @author HE176322
 */
public final class Main {

    // Private constructor: Main only has static methods (checklist 3.4).
    private Main() {
    }

    // Starts the program: checks requests until the user answers N.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ShowroomController controller = new ShowroomController();
        CarRequestDTO requestDTO = null;
        boolean findMore = true;

        // the title and the heading, printed once
        System.out.println(Message.TITLE);
        System.out.println(Message.INPUT_INFO);

        // one request per turn, until the user answers N
        while (findMore) {
            requestDTO = inputRequest(sc);

            // a refused request prints the brief's two refusal lines
            try {
                controller.checkCar(requestDTO);
            } catch (CarException e) {
                // "Can't sell Car" then the reason carried by the exception
                System.out.println(Message.CANT_SELL);
                System.out.println(e.getMessage());
            }

            findMore = inputFindMore(sc);
        }
    }

    // Function 1 of the brief: reads the four lines of one request. The Hint: a name that
    // is not a Car / Color / Day becomes null through getCar / getColor / getDay.
    private static CarRequestDTO inputRequest(Scanner sc) {
        CarRequestDTO requestDTO = new CarRequestDTO();

        // name, colour, price (kept as typed for checkCar) and day
        System.out.print(Message.INPUT_NAME);
        requestDTO.setCar(Car.getCar(sc.nextLine()));
        System.out.print(Message.INPUT_COLOR);
        requestDTO.setColor(Color.getColor(sc.nextLine()));
        System.out.print(Message.INPUT_PRICE);
        requestDTO.setPrice(sc.nextLine());
        System.out.print(Message.INPUT_TODAY);
        requestDTO.setDay(Day.getDay(sc.nextLine()));

        // the blank line of the brief's screen before the result
        System.out.println();
        return requestDTO;
    }

    // Asks "Do you want find more?(Y/N):" until the answer is Y or N.
    private static boolean inputFindMore(Scanner sc) {
        String line = "";

        // keep asking until the answer is Y or N
        while (true) {
            System.out.print(Message.FIND_MORE);
            line = sc.nextLine();

            // any other answer prints the reason and asks again
            try {
                return Validation.checkYesNo(line);
            } catch (Exception e) {
                // "Please input Y or N."
                System.out.println(e.getMessage());
            }
        }
    }
}
