package main;

import constants.Constants;
import constants.Message;
import controller.BillController;
import dto.BillRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow - read the bills and the wallet (Function 1), then call the
 * controller once (Function 2).
 *
 * @author HE176322
 */
public class Main {

    // Starts the program.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BillController controller = new BillController();
        System.out.println(Message.TITLE);
        BillRequestDTO dto = new BillRequestDTO();
        int numberOfBill = inputNumberOfBill(sc);
        dto.setBills(inputBills(sc, numberOfBill));
        dto.setWalletAmount(inputWallet(sc));
        controller.calculateBill(dto);
    }

    // Asks for the number of bills until it is a whole number from 1 to 100.
    private static int inputNumberOfBill(Scanner sc) {
        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_NUMBER_OF_BILL);
            String line = sc.nextLine();
            // a wrong line prints the reason and loops again
            try {
                return Validation.getInt(line, Constants.MIN_BILLS,
                        Constants.MAX_BILLS);
            } catch (Exception e) {
                // "You must input a number." or the range message
                System.out.println(e.getMessage());
            }
        }
    }

    // Reads the value of every bill.
    private static int[] inputBills(Scanner sc, int numberOfBill) {
        int[] bills = new int[numberOfBill];
        // one bill per turn; the user counts bills from 1, the array from 0
        for (int i = 0; i < numberOfBill; i++) {
            bills[i] = inputBill(sc, i + 1);
        }
        return bills;
    }

    // Asks for the value of one bill until it is a legal whole number.
    private static int inputBill(Scanner sc, int billNumber) {
        // keep asking the SAME bill until Validation accepts the line
        while (true) {
            System.out.print(String.format(Message.INPUT_BILL, billNumber));
            String line = sc.nextLine();
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
        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_WALLET);
            String line = sc.nextLine();
            // a wrong line prints the reason and loops again
            try {
                return Validation.getInt(line, Constants.MIN_WALLET,
                        Constants.MAX_WALLET);
            } catch (Exception e) {
                // "You must input a number." or the range message
                System.out.println(e.getMessage());
            }
        }
    }
}
