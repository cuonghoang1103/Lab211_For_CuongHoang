package main;

import constants.Constants;
import constants.Message;
import controller.BillController;
import dto.BillRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow - read the bills and the wallet (Function 1), then call the
 * controller once (Function 2). Every keyboard read and every validation happen here.
 *
 * @author HE176322
 */
public final class Main {

    // Private constructor: Main only has static methods (checklist 3.4).
    private Main() {
    }

    // Starts the program: reads the bills and the wallet into the request, then calls the
    // controller once.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BillController controller = new BillController();
        BillRequestDTO requestDTO = new BillRequestDTO();
        int numberOfBill = 0;

        // Function 1: the title, then how many bills, the value of each bill and the wallet
        System.out.println(Message.TITLE);
        numberOfBill = inputNumberOfBill(sc);
        requestDTO.setBillArray(inputBillArray(sc, numberOfBill));
        requestDTO.setWalletAmount(inputWallet(sc));

        // Function 2: total, compare with the wallet, show the message - one controller call
        controller.calculateBill(requestDTO);
    }

    // Asks for the number of bills until it is a whole number from 1 to 100.
    private static int inputNumberOfBill(Scanner sc) {
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_NUMBER_OF_BILL);
            line = sc.nextLine();

            // a wrong line prints the reason and loops again
            try {
                return Validation.getInt(line, Constants.MIN_BILLS, Constants.MAX_BILLS);
            } catch (Exception e) {
                // "You must input a number." or the range message
                System.out.println(e.getMessage());
            }
        }
    }

    // Reads the value of every bill.
    private static int[] inputBillArray(Scanner sc, int numberOfBill) {
        int[] billArray = new int[numberOfBill];

        // one bill per turn; the user counts bills from 1, the array from 0
        for (int i = 0; i < numberOfBill; i++) {
            billArray[i] = inputBill(sc, i + 1);
        }

        return billArray;
    }

    // Asks for the value of one bill until it is a legal whole number.
    private static int inputBill(Scanner sc, int billNumber) {
        String line = "";

        // keep asking the SAME bill until Validation accepts the line
        while (true) {
            System.out.print(String.format(Message.INPUT_BILL, billNumber));
            line = sc.nextLine();

            // a wrong line prints the reason and loops again
            try {
                return Validation.getInt(line, Constants.MIN_BILL_VALUE,
                        Constants.MAX_BILL_VALUE);
            } catch (Exception e) {
                // "You must input a number." or the range message
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the amount in the wallet until it is a legal whole number.
    private static int inputWallet(Scanner sc) {
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_WALLET);
            line = sc.nextLine();

            // a wrong line prints the reason and loops again
            try {
                return Validation.getInt(line, Constants.MIN_WALLET, Constants.MAX_WALLET);
            } catch (Exception e) {
                // "You must input a number." or the range message
                System.out.println(e.getMessage());
            }
        }
    }
}
