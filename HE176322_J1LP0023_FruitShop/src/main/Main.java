package main;

import constants.Constants;
import constants.Message;
import controller.ShopController;
import dto.FruitRequestDTO;
import dto.OrderRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow of the program - the menu loop and the keyboard.
 *
 * @author HE176322
 */
public class Main {

    // Starts the program: shows the main screen until the user chooses Exit.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ShopController controller = new ShopController();
        boolean running = true;
        // show the main screen again after every function, until Exit
        while (running) {
            System.out.println(Message.MENU);
            int choice = inputChoice(sc);
            // any business error of the chosen function is shown here
            try {
                // run the function the user picked
                switch (choice) {
                    // option 1: the owner creates fruits
                    case Constants.MENU_CREATE:
                        createFruits(sc, controller);
                        break;
                    // option 2: the owner views the orders
                    case Constants.MENU_VIEW_ORDERS:
                        controller.viewOrders();
                        break;
                    // option 3: a buyer shops
                    case Constants.MENU_SHOPPING:
                        shopping(sc, controller);
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
                // the reason written in Message, like "There is no order yet."
                System.out.println(e.getMessage());
            }
        }
    }

    // Option 1: creates fruits until the owner answers N, then lists every fruit.
    private static void createFruits(Scanner sc, ShopController controller) {
        // one fruit per round, until the owner answers N
        do {
            submitFruit(controller, inputFruit(sc, controller));
        } while (inputYesNo(sc, Message.ASK_CONTINUE)); // Y repeats, N leaves the loop
        controller.displayStock();
    }

    // Reads the five fields of a new fruit; the id is checked as soon as it is typed.
    private static FruitRequestDTO inputFruit(Scanner sc, ShopController controller) {
        FruitRequestDTO dto = new FruitRequestDTO();
        dto.setFruitId(inputFruitId(sc, controller));
        dto.setFruitName(inputText(sc, Message.INPUT_FRUIT_NAME));
        dto.setPrice(inputPrice(sc));
        dto.setQuantity(inputStock(sc));
        dto.setOrigin(inputText(sc, Message.INPUT_ORIGIN));
        return dto;
    }

    // Sends one fruit to the controller.
    private static void submitFruit(ShopController controller, FruitRequestDTO dto) {
        // the service may still refuse the fruit
        try {
            controller.createFruit(dto);
        } catch (Exception e) {
            // show why the fruit was refused
            System.out.println(e.getMessage());
        }
    }

    // Option 3: shows the fruits and fills the cart until the buyer orders (Y) or
    // leaves (item 0).
    private static void shopping(Scanner sc, ShopController controller) throws Exception {
        controller.startShopping();
        // one fruit per round, until the buyer orders or chooses 0
        while (true) {
            int count = controller.displayFruitList();
            OrderRequestDTO dto = new OrderRequestDTO();
            dto.setItemNumber(inputItem(sc, count));
            // item 0: back to the main screen, the cart is thrown away
            if (dto.getItemNumber() == Constants.RETURN_ITEM) {
                controller.cancelShopping();
                return;
            }
            controller.selectFruit(dto);
            dto.setQuantity(inputOrderQuantity(sc));
            // not enough in stock: show the list again
            if (!submitCartItem(controller, dto)) {
                continue;
            }
            // Y: show the cart, ask the name, save the order; N: show the list again
            if (inputYesNo(sc, Message.ASK_ORDER_NOW)) {
                controller.displayCart();
                dto.setCustomerName(inputText(sc, Message.INPUT_NAME));
                controller.placeOrder(dto);
                return;
            }
        }
    }

    // Adds the chosen fruit to the cart; false when the stock cannot cover it.
    private static boolean submitCartItem(ShopController controller, OrderRequestDTO dto) {
        // the service refuses more than what is left
        try {
            controller.addToCart(dto);
            return true;
        } catch (Exception e) {
            // "Coconut is out of stock." or "Only 5 Orange left in stock."
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Asks for a menu choice until it is a number from 1 to 4.
    private static int inputChoice(Scanner sc) {
        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_CHOICE);
            String line = sc.nextLine();
            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.MENU_MIN, Constants.MENU_EXIT);
            } catch (Exception e) {
                // "You must input a number." or "Please choose from 1 to 4."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for an item number until it is from 0 to the number of fruits.
    private static int inputItem(Scanner sc, int count) {
        // keep asking until the item exists (or is 0)
        while (true) {
            System.out.print(Message.INPUT_ITEM);
            String line = sc.nextLine();
            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.RETURN_ITEM, count);
            } catch (Exception e) {
                // "You must input a number." or "Please choose from 0 to 4."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the fruit id until it is not blank and not used yet.
    private static String inputFruitId(Scanner sc, ShopController controller) {
        FruitRequestDTO dto = new FruitRequestDTO();
        // keep asking until the id is free
        while (true) {
            System.out.print(Message.INPUT_FRUIT_ID);
            String line = sc.nextLine();
            // a blank or taken id prints the reason and loops again
            try {
                dto.setFruitId(Validation.getText(line));
                controller.checkFruitId(dto);
                return dto.getFruitId();
            } catch (Exception e) {
                // "This field must not be empty." or "Fruit ID F001 already exists."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a text until it is not blank.
    private static String inputText(Scanner sc, String prompt) {
        // keep asking until something is typed
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine();
            // a blank line prints the reason and loops again
            try {
                return Validation.getText(line);
            } catch (Exception e) {
                // "This field must not be empty."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the price until it is a number greater than 0.
    private static double inputPrice(Scanner sc) {
        // keep asking until the price is legal
        while (true) {
            System.out.print(Message.INPUT_PRICE);
            String line = sc.nextLine();
            // a wrong price prints the reason and loops again
            try {
                return Validation.getPrice(line);
            } catch (Exception e) {
                // "You must input a number." or "Price must be greater than 0."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the quantity in stock until it is a whole number of 0 or more.
    private static int inputStock(Scanner sc) {
        // keep asking until the quantity is legal
        while (true) {
            System.out.print(Message.INPUT_STOCK);
            String line = sc.nextLine();
            // a wrong quantity prints the reason and loops again
            try {
                return Validation.getStock(line);
            } catch (Exception e) {
                // "You must input a number." or "Quantity must not be negative."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the quantity to buy until it is a whole number greater than 0.
    private static int inputOrderQuantity(Scanner sc) {
        // keep asking until the quantity is legal
        while (true) {
            System.out.print(Message.INPUT_ORDER_QUANTITY);
            String line = sc.nextLine();
            // a wrong quantity prints the reason and loops again
            try {
                return Validation.getOrderQuantity(line);
            } catch (Exception e) {
                // "You must input a number." or "Quantity must be greater than 0."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks a (Y/N) question until the answer is Y or N.
    private static boolean inputYesNo(Scanner sc, String question) {
        // keep asking until the answer is Y or N
        while (true) {
            System.out.print(question);
            String line = sc.nextLine();
            // anything else prints "Please enter Y or N." and loops again
            try {
                return Validation.getYesNo(line);
            } catch (Exception e) {
                // show which letters are allowed
                System.out.println(e.getMessage());
            }
        }
    }
}
