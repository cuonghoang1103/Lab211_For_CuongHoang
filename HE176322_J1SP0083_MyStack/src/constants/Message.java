package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- menu (the brief's sample, shown once when the program starts) -----
    // Title, options and closing line of the menu.
    public static final String MENU = "======= STACK DEMO (MyStack) =======\n"
            + "1.Push   2.Pop   3.Get(peek)   4.Display   0.Exit\n"
            + "====================================";

    // Prompt for the menu choice.
    public static final String INPUT_CHOICE = "Choose: ";

    // Prompt for the value to push.
    public static final String INPUT_VALUE = "Enter value: ";

    // ----- validation errors -----
    // The line typed was not a whole number.
    public static final String INVALID_NUMBER = "You must input a number.";

    // Menu choice outside the allowed range; %d are the bounds.
    public static final String INVALID_RANGE = "Value must be between %d and %d.";

    // ----- business error -----
    // pop() or get() on an empty stack (the brief: "print a clear message").
    public static final String STACK_EMPTY = "Stack is empty.";

    // ----- results (the brief's sample run) -----
    // After a push; %d is the value, %s the stack from top to bottom.
    public static final String PUSHED = "Pushed %d.   Stack (top -> bottom): %s";

    // After a pop; %d is the value removed, %s the stack that is left.
    public static final String POPPED = "Popped %d.   Stack (top -> bottom): %s";

    // After a get (peek); %d is the top value, which stays in the stack.
    public static final String TOP = "Get (top) = %d           (not removed)";

    // Option 4; %s is the stack from top to bottom.
    public static final String STACK = "Stack (top -> bottom): %s";

    // Shown when the user exits.
    public static final String GOODBYE = "Goodbye!";

    // Private constructor: this class only holds constants, so nobody should ever create
    // an object of it.
    private Message() {
    }
}
