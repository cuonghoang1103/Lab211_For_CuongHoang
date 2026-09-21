package main;

import constants.Constants;
import constants.Message;
import controller.ShopController;
import dto.FruitRequestDTO;
import dto.OrderRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow of the program - the menus and the keyboard. Every keyboard read
 * and every validation happen here; each flow then calls the controller once.
 *
 * @author HE176322
 */
public final class Main {

    // Private constructor: Main only has static methods (checklist 3.4).
    private Main() {
    }

    // Starts the program: shows the main screen until the user chooses Exit.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ShopController controller = new ShopController();
        boolean running = true;
        int choice = 0;

        // show the main screen again after every function, until Exit
        while (running) {
            System.out.println(Message.MENU);
            choice = inputChoice(sc);

            // any business error of the chosen function is shown here
            try {
                // run the function the user picked: one call to the controller per flow
                switch (choice) {
                    // option 1: the create screen (one call to the controller per fruit);
                    // the answer N ends it, then every fruit created is shown (the brief)
                    case Constants.MENU_CREATE:
                        createFruits(sc, controller);
                        controller.displayFruits();
                        break;

                    // option 2: every order, customer by customer
                    case Constants.MENU_VIEW_ORDERS:
                        controller.displayOrders();
                        break;

                    // option 3: the shopping screen, a sub menu of its own
                    case Constants.MENU_SHOPPING:
                        buyFruits(sc, controller);
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

    // Option 1: one fruit per round (one call to the controller each), until the answer
    // to "Do you want to continue (Y/N)?" is N.
    private static void createFruits(Scanner sc, ShopController controller) {
        FruitRequestDTO requestDTO = null;
        boolean creating = true;

        // one fruit per round
        while (creating) {
            requestDTO = inputFruit(sc, controller);
            createFruit(controller, requestDTO);

            // the brief: Y continues, N returns to the main screen
            creating = inputYesNo(sc, Message.ASK_CONTINUE);
        }
    }

    // Reads the five fields of a new fruit; the id is checked as soon as it is typed.
    private static FruitRequestDTO inputFruit(Scanner sc, ShopController controller) {
        FruitRequestDTO requestDTO = new FruitRequestDTO();

        // the id first (a taken id is asked again at once), then the other four fields
        requestDTO.setFruitId(inputFruitId(sc, controller));
        requestDTO.setFruitName(inputText(sc, Message.INPUT_FRUIT_NAME));
        requestDTO.setPrice(inputPrice(sc));
        requestDTO.setQuantity(inputStock(sc));
        requestDTO.setOrigin(inputText(sc, Message.INPUT_ORIGIN));
        return requestDTO;
    }

    // Asks for the fruit id until it is not blank and no fruit uses it yet. A taken id is
    // reported at once, before the other fields, through a check-only call.
    private static String inputFruitId(Scanner sc, ShopController controller) {
        FruitRequestDTO requestDTO = new FruitRequestDTO();
        boolean free = false;

        // keep asking until the id is free
        while (!free) {
            requestDTO.setFruitId(inputText(sc, Message.INPUT_FRUIT_ID));
            free = checkFruitId(controller, requestDTO);
        }

        return requestDTO.getFruitId();
    }

    // Option 1, check only (no render, nothing stored): true when no fruit uses the id; a
    // taken id prints "Fruit ID F001 already exists.".
    private static boolean checkFruitId(ShopController controller,
            FruitRequestDTO requestDTO) {
        // the service throws when another fruit has the id
        try {
            controller.checkFruitId(requestDTO);
            return true;
        } catch (Exception e) {
            // "Fruit ID F001 already exists."
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Option 1, one round: sends one new fruit to the controller; a fruit refused is
    // reported and the create screen goes on.
    private static void createFruit(ShopController controller, FruitRequestDTO requestDTO) {
        // the service may still refuse it
        try {
            controller.createFruit(requestDTO);
        } catch (Exception e) {
            // show why the fruit was refused
            System.out.println(e.getMessage());
        }
    }

    // Option 3: the buyer's shopping screen - a sub menu whose menu is the fruit list.
    // One round per item chosen, until the buyer orders (answer Y) or goes back (item
    // 0); each step of a round is its own flow: one call to the controller.
    private static void buyFruits(Scanner sc, ShopController controller) throws Exception {
        OrderRequestDTO requestDTO = new OrderRequestDTO();

        // one round per item chosen
        while (true) {
            requestDTO.setItemNumber(chooseItem(sc, controller));

            // item 0: back to the main screen, a cart with fruits is thrown away
            if (requestDTO.getItemNumber() == Constants.RETURN_ITEM) {
                cancelShopping(controller);
                return;
            }

            // "You selected: Coconut", then how many
            selectFruit(controller, requestDTO);
            requestDTO.setQuantity(inputOrderQuantity(sc));

            // in stock, and Y to "Do you want to order now (Y/N)": the cart, the name,
            // then the order; not in stock, or N: the fruit list again
            if (addToCart(controller, requestDTO) && inputYesNo(sc, Message.ASK_ORDER_NOW)) {
                requestDTO.setCustomerName(inputCustomerName(sc, controller));
                saveOrder(controller, requestDTO);
                return;
            }
        }
    }

    // Option 3, a round begins: the buyer's "List of Fruit" (the menu of this sub menu;
    // an empty shop throws "There is no fruit in the shop yet."), then the item.
    private static int chooseItem(Scanner sc, ShopController controller) throws Exception {
        int itemCount = controller.displayFruitList();

        // the legal items are 0..the number of fruits listed
        return inputItem(sc, itemCount);
    }

    // Option 3, item 0: back to the main screen; a cart with fruits is thrown away.
    private static void cancelShopping(ShopController controller) {
        controller.cancelShopping();
    }

    // Option 3, an item chosen: the brief's "You selected: Coconut".
    private static void selectFruit(ShopController controller, OrderRequestDTO requestDTO) {
        controller.selectFruit(requestDTO);
    }

    // Option 3, a quantity typed: into the cart; true when the stock covers it,
    // otherwise the reason is shown and the answer is false.
    private static boolean addToCart(ShopController controller, OrderRequestDTO requestDTO) {
        // the service refuses more than what is left
        try {
            controller.addToCart(requestDTO);
            return true;
        } catch (Exception e) {
            // "Coconut is out of stock." or "Only 5 Orange left in stock."
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Option 3, the answer Y: the cart with its total (the brief's screen), then the name
    // that finishes the order.
    private static String inputCustomerName(Scanner sc, ShopController controller) {
        controller.displayCart();

        // the brief: "Customer inputs his/her name to finish ordering"
        return inputText(sc, Message.INPUT_NAME);
    }

    // Option 3, the name typed: the cart becomes the customer's order.
    private static void saveOrder(ShopController controller, OrderRequestDTO requestDTO) {
        controller.saveOrder(requestDTO);
    }

    // Asks for a menu choice until it is a number from 1 to 4.
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
                // "You must input a number." or "Please choose from 1 to 4."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for an item number until it is from 0 to the number of fruits.
    private static int inputItem(Scanner sc, int itemCount) {
        String line = "";

        // keep asking until the item exists (or is 0)
        while (true) {
            System.out.print(Message.INPUT_ITEM);
            line = sc.nextLine();

            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.RETURN_ITEM, itemCount);
            } catch (Exception e) {
                // "You must input a number." or "Please choose from 0 to 4."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a text until it is not blank.
    private static String inputText(Scanner sc, String prompt) {
        String line = "";

        // keep asking until something is typed
        while (true) {
            System.out.print(prompt);
            line = sc.nextLine();

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
        String line = "";

        // keep asking until the price is legal
        while (true) {
            System.out.print(Message.INPUT_PRICE);
            line = sc.nextLine();

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
        String line = "";

        // keep asking until the quantity is legal
        while (true) {
            System.out.print(Message.INPUT_STOCK);
            line = sc.nextLine();

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
        String line = "";

        // keep asking until the quantity is legal
        while (true) {
            System.out.print(Message.INPUT_ORDER_QUANTITY);
            line = sc.nextLine();

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
        String line = "";

        // keep asking until the answer is Y or N
        while (true) {
            System.out.print(question);
            line = sc.nextLine();

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
