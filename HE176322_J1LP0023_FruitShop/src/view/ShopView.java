package view;

import constants.Constants;
import constants.Message;
import dto.FruitResponseDTO;
import dto.ItemResponseDTO;
import dto.OrderResponseDTO;
import dto.ShopResponseDTO;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

/**
 * VIEW: the only place (with main) allowed to print results. It receives the data through
 * its attribute (the ResponseDTO, as in the Guide sample), never through the parameters of
 * display().
 *
 * @author HE176322
 */
public class ShopView {

    // Money the brief's way ("2$", "2.5$"), with a dot on every machine's locale.
    private DecimalFormat moneyFormat;

    // The answer to print, handed over by the controller.
    private ShopResponseDTO responseDTO;

    // Creates the view; Locale.US keeps "2.5$" even on a Vietnamese machine.
    public ShopView() {
        moneyFormat = new DecimalFormat(Constants.MONEY_PATTERN,
                DecimalFormatSymbols.getInstance(Locale.US));
    }

    // Receives the answer the next display() call will print.
    public void setResponseDTO(ShopResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints what the controller set, in this order: the result line, the owner's table,
    // the buyer's list, the cart, the orders.
    public void display() {
        // a one-line result such as "Fruit F001 has been created."
        if (responseDTO.getMessage() != null) {
            System.out.println(responseDTO.getMessage());
        }

        // option 1, answer N: every fruit with its stock
        if (responseDTO.getStockList() != null) {
            displayStockList();
        }

        // option 3: the buyer's "List of Fruit"
        if (responseDTO.getFruitList() != null) {
            displayFruitList();
        }

        // option 3, answer Y: the cart with its total
        if (responseDTO.getCart() != null) {
            displayCart();
        }

        // option 2: every order
        if (responseDTO.getOrderList() != null) {
            displayOrderList();
        }
    }

    // Prints the owner's table: every fruit with its quantity in stock.
    private void displayStockList() {
        System.out.println(Message.LIST_TITLE);
        System.out.println(Message.STOCK_HEADER);

        // one row per fruit
        for (FruitResponseDTO fruit : responseDTO.getStockList()) {
            System.out.println(String.format(Constants.STOCK_ROW_FORMAT, fruit.getItemNumber(),
                    fruit.getFruitName(), fruit.getOrigin(), formatMoney(fruit.getPrice()),
                    fruit.getQuantity()));
        }
    }

    // Prints the buyer's "List of Fruit": item, name, origin, price.
    private void displayFruitList() {
        System.out.println(Message.LIST_TITLE);
        System.out.println(Message.LIST_HEADER);

        // one row per fruit
        for (FruitResponseDTO fruit : responseDTO.getFruitList()) {
            System.out.println(String.format(Constants.LIST_ROW_FORMAT, fruit.getItemNumber(),
                    fruit.getFruitName(), fruit.getOrigin(), formatMoney(fruit.getPrice())));
        }
    }

    // Prints the cart the brief shows after "Do you want to order now (Y/N)": Y - the
    // header and the rows one space in, like the brief, then the total.
    private void displayCart() {
        OrderResponseDTO cart = responseDTO.getCart();

        System.out.println(Message.CART_HEADER);

        // one row per fruit in the cart
        for (ItemResponseDTO item : cart.getItemList()) {
            System.out.println(String.format(Constants.CART_ROW_FORMAT, item.getFruitName(),
                    item.getQuantity(), formatMoney(item.getPrice()),
                    formatMoney(item.getAmount())));
        }

        System.out.println(String.format(Message.TOTAL, formatMoney(cart.getTotal())));
    }

    // Prints every order like the brief's View orders: customer, numbered lines, total,
    // with an empty line between two customers (the brief's screen).
    private void displayOrderList() {
        ArrayList<OrderResponseDTO> orderList = responseDTO.getOrderList();

        // one block per customer
        for (int i = 0; i < orderList.size(); i++) {
            // the brief leaves an empty line between two customers
            if (i > 0) {
                System.out.println();
            }

            displayOrder(orderList.get(i));
        }
    }

    // Prints one order: "Customer: ...", the header, the lines numbered from 1, the total.
    private void displayOrder(OrderResponseDTO order) {
        ArrayList<ItemResponseDTO> itemList = order.getItemList();
        ItemResponseDTO item = null;

        System.out.println(String.format(Message.CUSTOMER, order.getCustomerName()));
        System.out.println(Message.ORDER_HEADER);

        // lines numbered from 1, like "1. Apple"
        for (int i = 0; i < itemList.size(); i++) {
            item = itemList.get(i);
            System.out.println(String.format(Constants.ORDER_ROW_FORMAT, i + 1,
                    item.getFruitName(), item.getQuantity(), formatMoney(item.getPrice()),
                    formatMoney(item.getAmount())));
        }

        System.out.println(String.format(Message.TOTAL, formatMoney(order.getTotal())));
    }

    // Writes an amount with the dollar sign after it, like "6$".
    private String formatMoney(double amount) {
        return String.format(Constants.MONEY_FORMAT, moneyFormat.format(amount));
    }
}
