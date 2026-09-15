package view;

import constants.Message;
import dto.StackResponseDTO;

/**
 * VIEW: prints the result of every stack operation.
 *
 * @author HE176322
 */
public class StackView {

    // The result to display, handed over by the controller.
    private StackResponseDTO response;

    // Receives the result the next display call will print.
    public void setResponse(StackResponseDTO response) {
        this.response = response;
    }

    // Prints "Pushed 10.
    public void displayPush() {
        System.out.println(String.format(Message.PUSHED, response.getValue(),
                response.getStack()));
    }

    // Prints "Popped 30.
    public void displayPop() {
        System.out.println(String.format(Message.POPPED, response.getValue(),
                response.getStack()));
    }

    // Prints "Get (top) = 30 (not removed)".
    public void displayTop() {
        System.out.println(String.format(Message.TOP, response.getValue()));
    }

    // Prints "Stack (top -> bottom): [20, 10]" (option 4).
    public void display() {
        System.out.println(String.format(Message.STACK, response.getStack()));
    }
}
