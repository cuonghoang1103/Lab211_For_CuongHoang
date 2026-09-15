package main;

import constants.Car;
import constants.Color;
import constants.Day;
import constants.Message;
import controller.ShowroomController;
import dto.CarRequestDTO;
import exceptions.ExceptionCar;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow - read one request, check it, ask "find more?".
 *
 * @author HE176322
 */
public class Main {

    // Starts the program: checks requests until the user answers N.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ShowroomController controller = new ShowroomController();
        System.out.println(Message.TITLE);
        System.out.println(Message.INPUT_INFO);
        boolean findMore = true;
        // one request per turn, until the user answers N
        while (findMore) {
            CarRequestDTO dto = inputRequest(sc);
            // a refused request prints the brief's two refusal lines
            try {
                controller.checkCar(dto);
            } catch (ExceptionCar e) {
                // "Can't sell Car" then the reason carried by the exception
                System.out.println(Message.CANT_SELL);
                System.out.println(e.getMessage());
            }
            findMore = inputFindMore(sc);
        }
    }

    // Reads the four lines of one request.
    private static CarRequestDTO inputRequest(Scanner sc) {
        CarRequestDTO dto = new CarRequestDTO();
        System.out.print(Message.INPUT_NAME);
        dto.setCar(Car.getCar(sc.nextLine()));
        System.out.print(Message.INPUT_COLOR);
        dto.setColor(Color.getColor(sc.nextLine()));
        System.out.print(Message.INPUT_PRICE);
        dto.setPrice(sc.nextLine());
        System.out.print(Message.INPUT_TODAY);
        dto.setDay(Day.getDay(sc.nextLine()));
        System.out.println();
        return dto;
    }

    // Asks "Do you want find more?(Y/N):" until the answer is Y or N.
    private static boolean inputFindMore(Scanner sc) {
        // keep asking until the answer is Y or N
        while (true) {
            System.out.print(Message.FIND_MORE);
            String line = sc.nextLine();
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
