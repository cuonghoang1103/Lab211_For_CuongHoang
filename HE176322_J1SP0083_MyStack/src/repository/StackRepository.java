package repository;

import constants.Message;
import dto.StackRequestDTO;
import dto.StackResponseDTO;
import model.MyStack;

/**
 * REPOSITORY: keeps the one stack of the program alive between menu choices and performs
 * the simple operations on it (push = create, pop = delete, get/display = read). It turns
 * the model's toString() into the result line for the controller; it never prints.
 *
 * @author HE176322
 */
public class StackRepository {

    // The "database": the stack itself.
    private MyStack myStack;

    // Creates the repository with an empty stack.
    public StackRepository() {
        myStack = new MyStack();
    }

    // Create: pushes the value of the request.
    public StackResponseDTO push(StackRequestDTO requestDTO) {
        int value = requestDTO.getValue();

        // put the value on the top: "Pushed 10.   Stack (top -> bottom): [10]"
        myStack.push(value);
        return toResponse(String.format(Message.PUSHED, value, myStack.toString()));
    }

    // Delete: pops the top value ("Stack is empty." is thrown by the model).
    public StackResponseDTO pop() throws Exception {
        int value = myStack.pop();

        // "Popped 30.   Stack (top -> bottom): [20, 10]" - the stack that is left
        return toResponse(String.format(Message.POPPED, value, myStack.toString()));
    }

    // Read: the top value without removing it ("Stack is empty." is thrown by the model).
    public StackResponseDTO get() throws Exception {
        int value = myStack.get();

        // "Get (top) = 30           (not removed)"
        return toResponse(String.format(Message.TOP, value));
    }

    // Read: the whole stack (option 4); no single value is involved.
    public StackResponseDTO getStack() {
        // "Stack (top -> bottom): [20, 10]", or "[]" when the stack is empty
        return toResponse(String.format(Message.STACK, myStack.toString()));
    }

    // Packs the result line into the DTO the view may see.
    private StackResponseDTO toResponse(String message) {
        StackResponseDTO responseDTO = new StackResponseDTO();

        // the one line the view will print for this flow
        responseDTO.setMessage(message);
        return responseDTO;
    }
}
