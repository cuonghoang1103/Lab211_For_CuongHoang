package view;

import constants.Constants;
import constants.Message;
import dto.FruitResponseDTO;
import dto.ItemResponseDTO;
import dto.OrderResponseDTO;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

/**
 * VIEW: the only place (with main) allowed to print results.
 *
 * @author HE176322
 */
public class ShopView {

    // Money the brief's way ("2$", "2.5$"), with a dot on every machine's locale.
    private DecimalFormat moneyFormat = new DecimalFormat(Constants.MONEY_PATTERN,
            DecimalFormatSymbols.getInstance(Locale.US));

    // Prints the owner's table: every fruit with its quantity in stock.
    public void displayStock(ArrayList<FruitResponseDTO> fruits) {
        System.out.println(Message.LIST_TITLE);
        // no fruit was created
        if (fruits.isEmpty()) {
            System.out.println(Message.NO_FRUIT);
            return;
        }
        System.out.println(Message.STOCK_HEADER);
        // one row per fruit
        for (FruitResponseDTO fruit : fruits) {
            System.out.println(String.format(Constants.STOCK_ROW_FORMAT, fruit.getItemNumber(),
                    fruit.getFruitName(), fruit.getOrigin(), formatMoney(fruit.getPrice()),
                    fruit.getQuantity()));
        }
    }

    // Prints the buyer's "List of Fruit": item, name, origin, price.
    public void displayFruitList(ArrayList<FruitResponseDTO> fruits) {
        System.out.println(Message.LIST_TITLE);
        System.out.println(Message.LIST_HEADER);
        // one row per fruit
        for (FruitResponseDTO fruit : fruits) {
            System.out.println(String.format(Constants.LIST_ROW_FORMAT, fruit.getItemNumber(),
                    fruit.getFruitName(), fruit.getOrigin(), formatMoney(fruit.getPrice())));
        }
    }

    // Prints the cart the brief shows after "Do you want to order now (Y/N)": Y.
    public void displayCart(OrderResponseDTO cart) {
        System.out.println(Message.ORDER_HEADER);
        // one row per fruit in the cart
        for (ItemResponseDTO item : cart.getItems()) {
            System.out.println(String.format(Constants.CART_ROW_FORMAT, item.getFruitName(),
                    item.getQuantity(), formatMoney(item.getPrice()),
                    formatMoney(item.getAmount())));
        }
        System.out.println(String.format(Message.TOTAL, formatMoney(cart.getTotal())));
    }

    // Prints every order like the brief's View orders: customer, numbered lines, total.
    public void displayOrders(ArrayList<OrderResponseDTO> orders) {
        // one block per customer
        for (OrderResponseDTO order : orders) {
            System.out.println(String.format(Message.CUSTOMER, order.getCustomerName()));
            System.out.println(Message.ORDER_HEADER);
            // lines numbered from 1, like "1. Apple"
            for (int i = 0; i < order.getItems().size(); i++) {
                ItemResponseDTO item = order.getItems().get(i);
                System.out.println(String.format(Constants.ORDER_ROW_FORMAT, i + 1,
                        item.getFruitName(), item.getQuantity(), formatMoney(item.getPrice()),
                        formatMoney(item.getAmount())));
            }
            System.out.println(String.format(Message.TOTAL, formatMoney(order.getTotal())));
        }
    }

    // Prints a one-line result such as "Fruit F001 has been created.".
    public void showMessage(String message) {
        System.out.println(message);
    }

    // Writes an amount with the dollar sign after it, like "6$".
    private String formatMoney(double amount) {
        return moneyFormat.format(amount) + Constants.CURRENCY;
    }
}
