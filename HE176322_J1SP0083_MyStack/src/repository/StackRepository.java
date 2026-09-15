package repository;

import dto.StackRequestDTO;
import dto.StackResponseDTO;
import model.MyStack;

/**
 * REPOSITORY: keeps the one stack of the program alive between menu choices and performs
 * the simple operations on it.
 *
 * @author HE176322
 */
public class StackRepository {

    // The "database": the stack itself.
    private MyStack myStack = new MyStack();

    // Creates the repository with an empty stack.
    public StackRepository() {
    }

    // Pushes the value of the request.
    public StackResponseDTO push(StackRequestDTO requestDTO) {
        myStack.push(requestDTO.getValue());
        return toResponse(requestDTO.getValue());
    }

    // Pops the top value.
    public StackResponseDTO pop() throws Exception {
        int value = myStack.pop();
        return toResponse(value);
    }

    // Reads the top value without removing it.
    public StackResponseDTO get() throws Exception {
        int value = myStack.get();
        return toResponse(value);
    }

    // Reads the whole stack (option 4); no single value is involved.
    public StackResponseDTO getStack() {
        StackResponseDTO response = new StackResponseDTO();
        response.setStack(myStack.toString());
        return response;
    }

    // Copies a value and the current stack into the DTO the view may see.
    private StackResponseDTO toResponse(int value) {
        StackResponseDTO response = new StackResponseDTO();
        response.setValue(value);
        response.setStack(myStack.toString());
        return response;
    }
}
